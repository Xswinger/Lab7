package ru.itmo.client;

import java.util.Scanner;

public class User {
    private static User instance = null;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }
    private static boolean creatingNewUser = false;
    private static boolean registrationStatus = false;
    private static String name;
    private static String password;

    public String getName() {
        return name;
    }

    public void inputName(){
        System.out.println("Input name:");
        String name = new Scanner(System.in).nextLine();
        if (!name.trim().equals("")) {
            User.getInstance().setName(name);
        } else {
            System.out.println("Wrong user name format");
            inputName();
        }
    }

    public void inputPassword() {
        System.out.println("Input password(Enter, if not password):");
        String password = new Scanner(System.in).nextLine();
        if (!password.trim().equals("")) {
            User.getInstance().setPassword(password);
        } else {
            User.getInstance().setPassword(null);
        }
    }

    public boolean isCreateNewUser() {
        return creatingNewUser;
    }

    public void setCreatingNewUser(boolean creatingNewUser) {
        User.creatingNewUser = creatingNewUser;
    }

    public void setName(String name) {
        User.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(boolean registrationStatus) {
        User.registrationStatus = registrationStatus;
    }
}
