package ru.itmo.client;

import ru.itmo.common.dto.Command;
import ru.itmo.common.dto.CreatingElement;

import java.util.Arrays;
import java.util.Scanner;

public class EnterCommand {
    //Выбор создания запроса команды или получения файла
    public static Command selectionRequest() {
        if (!User.getInstance().isRegistrationStatus()) {
            User.getInstance().inputName();
            User.getInstance().inputPassword();
            if (User.getInstance().isCreateNewUser()) {
                return new Command(Client.getInstance().getUserUUID(), true, false, User.getInstance().getName(),
                        User.getInstance().getPassword(), "creating new user");
            }
            else {return new Command(Client.getInstance().getUserUUID(),false, false, User.getInstance().getName(),
                    User.getInstance().getPassword(), "registration");
            }
        } else {
            return enterCommand();
        }
    }

    //передавать как enum имя команды
    public static Command enterCommand() {
        try {
            System.out.println("Enter command:");
            Scanner lineWithCommand = new Scanner(System.in);
            String inputData = lineWithCommand.nextLine();
            Command inputCommand;
            if (inputData.split(" ").length == 2) {
                inputCommand = new Command(Client.getInstance().getUserUUID(),true, User.getInstance().getName(),
                        User.getInstance().getPassword(), inputData.split(" ")[0], inputData.split(" ")[1]);
            } else {
                inputCommand = new Command(Client.getInstance().getUserUUID(),true, User.getInstance().getName(),
                        User.getInstance().getPassword(), inputData.split(" ")[0]);
            }
            if (Command.checkCommand()) {
                if (Arrays.stream(new String[]{"add", "add_if_max", "remove_greater"}).anyMatch(s ->
                        s.equals(inputCommand.getNameOfCommand()))) {
                    return new Command(Client.getInstance().getUserUUID(),true, User.getInstance().getName(),
                            User.getInstance().getPassword(), inputCommand.getNameOfCommand(),
                            CreatingElement.manualCreatingElement());
                } else if (inputCommand.getNameOfCommand().equals("update")) {
                    return new Command(Client.getInstance().getUserUUID(),true, User.getInstance().getName(),
                            User.getInstance().getPassword(), inputCommand.getNameOfCommand(),
                            inputCommand.getParameterOfCommand(), CreatingElement.manualCreatingElement());
                } else {
                    return inputCommand;
                }
            } else {
                return new Command(Client.getInstance().getUserUUID(),false, User.getInstance().getName(), User.getInstance().getPassword(),
                        "Unknown command", "Unknown command");
            }
        } catch (NumberFormatException ex) {
            return new Command(Client.getInstance().getUserUUID(),false, User.getInstance().getName(), User.getInstance().getPassword(),
                    "Invalid input variable format for this parameter");
        }
    }
}
