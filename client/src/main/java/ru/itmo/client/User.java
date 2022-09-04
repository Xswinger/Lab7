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
    private boolean creatingNewUser = false;
    private boolean registrationStatus = false;
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void inputName(){
        System.out.println("Input name:");
        String name = new Scanner(System.in).nextLine();
        if (!name.trim().equals("")) {
            this.setName(name);
        } else {
            System.out.println("Wrong user name format");
            inputName();
        }
    }

    public void inputPassword() {
        System.out.println("Input password(Enter, if not password):");
        String password = new Scanner(System.in).nextLine();
        if (!password.trim().equals("")) {
            this.setPassword(password);
        } else {
            this.setPassword(null);
        }
    }

    public boolean isCreateNewUser() {
        return creatingNewUser;
    }

    public void setCreatingNewUser(boolean creatingNewUser) {
        this.creatingNewUser = creatingNewUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(boolean registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
