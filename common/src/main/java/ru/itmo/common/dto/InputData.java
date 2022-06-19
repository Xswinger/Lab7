package ru.itmo.common.dto;

import ru.itmo.common.dto.locations.locationTo.Location;

import java.util.Scanner;

public class InputData {
    private static final Scanner SCANNER = new Scanner(System.in);

    //Ввод значений каждого поля объекта
    public static String inputName() {
        try {
            System.out.println("Enter name:");
            return Route.checkName(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of field 'name'");
            return inputName();
        }
    }

    public static String inputLocationName() {
        try {
            System.out.println("Enter Location name:");
            return Location.checkName(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'name'");
            return inputLocationName();
        }
    }

    public static Object inputLocationFromX() {
        try {
            System.out.println("Enter Location coordinate x: (optional, press Enter to skip input Location)");
            String input = SCANNER.nextLine().trim();
            if (input.equals("")) {
                return "none";
            } else {
                return Float.parseFloat(input);
            }
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'x'");
            return inputLocationFromX();
        }
    }

    public static double inputLocationFromY() {
        try {
            System.out.println("Enter Location coordinate y:");
            return Double.parseDouble(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'y'");
            return inputLocationFromY();
        }
    }

    public static Integer inputLocationFromZ() {
        try {
            System.out.println("Enter Location coordinate z:");
            return Integer.parseInt(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'z'");
            return inputLocationFromZ();
        }
    }

    public static Integer inputLocationToX() {
        try {
            System.out.println("Enter Location coordinate x:");
            return Integer.parseInt(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'x'");
            return inputLocationToX();
        }
    }

    public static long inputLocationToY() {
        try {
            System.out.println("Enter Location coordinate y:");
            return Long.parseLong(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'y'");
            return inputLocationToY();
        }
    }

    public static float inputLocationToZ() {
        try {
            System.out.println("Enter Location coordinate z:");
            return Float.parseFloat(SCANNER.nextLine().trim());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Location's field 'z'");
            return inputLocationToZ();
        }
    }

    public static long inputCoordinatesX() {
        try {
            System.out.println("Enter Coordinates x:");
            return Coordinates.checkX(Long.parseLong(SCANNER.nextLine().trim()));
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Coordinate's field 'x'");
            return inputCoordinatesX();
        }
    }

    public static Integer inputCoordinatesY() {
        try {
            System.out.println("Enter Coordinates y:");
            return Coordinates.checkY(Integer.parseInt(SCANNER.nextLine().trim()));
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of Coordinate's field 'y'");
            return inputCoordinatesY();
        }
    }

    public static Integer inputDistance() {
        try {
            System.out.println("Enter Distance:");
            return Route.checkDistance(Integer.parseInt(SCANNER.nextLine().trim()));
        }
        catch (NumberFormatException e){
            System.out.println("Invalid format of field 'distance'");
            return inputDistance();
        }
    }
}
