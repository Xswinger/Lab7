package ru.itmo.client;

import java.io.IOException;
import java.util.*;

/**
 * Класс запуска работы программы
 */
public class Manager {

    /**
     * Класс Manager
     *
     * @param args - аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("Start client...");
        try {
            while (true) {
                startConnection();
                System.out.println("Trying reconnect?(Y/N)");
                String answer = new Scanner(System.in).nextLine();
                if (answer.equals("N")) {
                    System.out.println("Exiting...");
                    break;
                } else if (!answer.equals("Y")) {
                    System.out.println("Unknown command");
                }
            }
        } catch (NoSuchElementException exception) {
            System.out.println("Incorrect command");
        } catch (IOException | ClassNotFoundException | InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
    }

    //Запуск канала
    private static void startConnection() throws IOException, ClassNotFoundException, InterruptedException {
        Client.channelInitialize();
    }
}
