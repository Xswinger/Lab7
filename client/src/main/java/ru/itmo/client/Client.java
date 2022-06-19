package ru.itmo.client;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.Message;
import ru.itmo.common.dto.Print;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.IntStream;

public class Client {
    private static boolean CONNECTION_SETUP;

    public static UUID getUserUUID() {
        return userUUID;
    }

    private static final UUID userUUID = UUID.randomUUID();

    private static DatagramChannel datagramChannel;
    private static final String HOST = "localhost";
    private static final int PORT = 63220;
    private static final SocketAddress SERVER_ADDRESS = new InetSocketAddress(HOST, PORT);
    private static UUID UUID_LAST_REQUEST;
    //Чекер состояния получения имени файла

    //Инициализация канала, цикл выборки поведения в зависимости от команды
    private static void userAuthorization(){
        if (!User.getInstance().isRegistrationStatus()) {
            System.out.println("Select authorization method:\nMake a new user(input '1')\nLog in as an existing user(input '2')");
            String response = new Scanner(System.in).nextLine();
            if (response.equals("1")) {
                User.getInstance().setCreatingNewUser(true);
            }
            else if (response.equals("2")) {
                    User.getInstance().setCreatingNewUser(false);
            } else {
                System.out.println("Unknown command");
                userAuthorization();
            }
        }
    }
    private static void requestProcessingCycle(ByteBuffer bufferReceive) throws IOException, InterruptedException, ClassNotFoundException {
        Message message;
        while (CONNECTION_SETUP){
            userAuthorization();
            Command sendingCommand = EnterCommand.selectionRequest();
            if (sendingCommand.getNameOfCommand() != null && sendingCommand.getNameOfCommand().equals("error")) {
                System.out.println(sendingCommand);
                continue;
            }
            if (sendingCommand.getNameOfCommand() != null &&
                    (sendingCommand.getNameOfCommand().equals("Unknown command") ||
                    sendingCommand.getNameOfCommand().equals("Invalid input variable format for this parameter"))) {
                System.out.println(sendingCommand.getNameOfCommand());
                continue;
            }
            try {
                message = sendCommandAndReceiveAnswer(bufferReceive, sendingCommand);
                UUID_LAST_REQUEST = message.getMessageUUID();
            }
            catch (PortUnreachableException e){
                User.getInstance().setRegistrationStatus(false);
                break;
            }
            setRegistrationStatus(message);
            if (message.getContentString() != null &&
                    message.getContentString().equals("Exiting...")) {
                System.out.println(Print.printContent(message));
                datagramChannel.disconnect();
                User.getInstance().setRegistrationStatus(false);
                System.exit(1);
            } else {
                System.out.println(Print.printContent(message));
            }
        }
    }
    public static void channelInitialize() throws IOException, ClassNotFoundException, InterruptedException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.connect(SERVER_ADDRESS);
        datagramChannel.configureBlocking(false);
        User.getInstance();
        ByteBuffer bufferReceive = ByteBuffer.allocate(5000);
        CONNECTION_SETUP = connectionToServer(bufferReceive);
        bufferReceive.clear();
        requestProcessingCycle(bufferReceive);
        System.out.println(Print.printContent(new Message(userUUID, false, "Connection lost")));
    }

    private static void setRegistrationStatus(Message message) {
        if (!User.getInstance().isRegistrationStatus() && message.getRegistrationStatus()) {
            User.getInstance().setRegistrationStatus(true);
        }
    }

    //Получение пакетов с данными
    private static Message sendCommandAndReceiveAnswer(ByteBuffer bufferReceive, Command sendingClass) throws IOException, InterruptedException, ClassNotFoundException {
        bufferReceive.clear();
        ByteBuffer bufferSend = ByteBuffer.wrap(Command.serialize(sendingClass));
        datagramChannel.write(bufferSend);
        Message message = waitingReceive(bufferReceive);
        UUID uuid1 = message.getUserUUID();
//        while (!uuid1.equals(userUUID)){
            message = waitingReceive(bufferReceive);
//        }
        bufferReceive.clear();
        return message;
    }

    private static Message waitingReceive(ByteBuffer bufferReceive) throws IOException, ClassNotFoundException, InterruptedException {
        Thread.sleep(50);
        datagramChannel.read(bufferReceive);
        while ((((Message) Message.deserialize(bufferReceive.array())).getMessageUUID().equals(UUID_LAST_REQUEST))) {
            datagramChannel.read(bufferReceive);
            Thread.sleep(50);
        }
        return (Message) Message.deserialize(bufferReceive.array());
    }

    //Установление соединения с сервером(отправка и принятие сообщения, всего 3 попытки)
    public static boolean connectionToServer(ByteBuffer bufferReceive) throws InterruptedException, IOException, ClassNotFoundException {
        System.out.println("Send request to server");
        datagramChannel.write(ByteBuffer.wrap(Command.serialize(new Command(userUUID, false,
                User.getInstance().getName(), User.getInstance().getPassword(), "check"))));
        for (int i = 0; i < 3; i++) {
            System.out.println("Waiting answer from server(Attempt № " + (i + 1) + ")");
            Thread.sleep(2000);
            try {
                datagramChannel.read(bufferReceive);
            }
            catch (PortUnreachableException ex){
                System.out.println("Fail to connect (port is unreachable)\n");
                continue;
            }
            UUID uuid = ((Message) Message.deserialize(bufferReceive.array())).
                    getUserUUID();
            if (IntStream.range(0, bufferReceive.array().length).parallel().allMatch(j ->
                    bufferReceive.array()[j] == 0)
//                    || !(uuid.equals(userUUID))
            ) {
                System.out.println("Fail to connect (buffer is empty)\n");
            } else {
                System.out.println("Connect successfully\n");
                Message message = (Message) Message.deserialize(bufferReceive.array());
                UUID_LAST_REQUEST = message.getMessageUUID();
                return true;
            }
        }
        return false;
    }
}
