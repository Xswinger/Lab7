package ru.itmo.common.dto;

import ru.itmo.common.dto.locations.locationFrom.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

public class CreatingElement {
    //Создание элемента коллекции через терминал
    public static Route manualCreatingElement() {
        String name = Route.checkName(InputData.inputName());
        Coordinates coordinates = new Coordinates(Coordinates.checkX(InputData.inputCoordinatesX()),
                Coordinates.checkY(InputData.inputCoordinatesY()));
        Date creationDate = new Date();
        Location from = null;
        Object X = InputData.inputLocationFromX();
        if (!X.toString().equals("none")) {
            from = new Location(Float.parseFloat(X.toString()), InputData.inputLocationFromY(),
                    Location.checkZ(InputData.inputLocationFromZ()));
        }
        ru.itmo.common.dto.locations.locationTo.Location to = new
                ru.itmo.common.dto.locations.locationTo.Location(ru.itmo.common.dto.locations.locationTo.Location.checkX(InputData.inputLocationToX()),
                InputData.inputLocationToY(), InputData.inputLocationToZ(), Route.checkName(InputData.inputLocationName()));
        int distance = Route.checkDistance(InputData.inputDistance());
        return checkFrom(from, X, name, coordinates, creationDate, to, distance);
    }

    //Создание элемента коллекции через скрипт
    public static Route scriptCreatingElement(BufferedReader bufferedReader) throws IOException {
        String bufferName = bufferedReader.readLine();
        String bufferCoordinateX = bufferedReader.readLine();
        String bufferCoordinateY = bufferedReader.readLine();
        String bufferFromX = bufferedReader.readLine();
        String bufferFromY = "";
        String bufferFromZ = "";
        Location from = null;
        if (!((Object) bufferFromX).toString().equals("")) {
            bufferFromY = bufferedReader.readLine();
            bufferFromZ = bufferedReader.readLine();
        }
        String bufferToX = bufferedReader.readLine();
        String bufferToY = bufferedReader.readLine();
        String bufferToZ = bufferedReader.readLine();
        String bufferToName = bufferedReader.readLine();
        String bufferDistance = bufferedReader.readLine();
        String name = Route.checkName(bufferName);
        Coordinates coordinates = new Coordinates(Coordinates.checkX(Long.parseLong(bufferCoordinateX)),
                Coordinates.checkY(Integer.parseInt(bufferCoordinateY)));
        Date creationDate = new Date();
        if (!((Object) bufferFromX).toString().equals("")) {
            from = new Location(Float.parseFloat(((Object) bufferFromX).toString()), Double.parseDouble(bufferFromY),
                    Integer.parseInt(bufferFromZ));
        }
        ru.itmo.common.dto.locations.locationTo.Location to = new
                ru.itmo.common.dto.locations.locationTo.Location(ru.itmo.common.dto.locations.locationTo.Location.checkX(Integer.parseInt(bufferToX)),
                Long.parseLong(bufferToY), Float.parseFloat(bufferToZ), Route.checkName(bufferToName));
        int distance = Route.checkDistance(Integer.parseInt(bufferDistance));
        return checkFrom(from, bufferFromX, name, coordinates, creationDate, to, distance);
    }

    private static Route checkFrom(Location from, Object x, String name, Coordinates coordinates, Date creationDate, ru.itmo.common.dto.locations.locationTo.Location to, int distance) {
        Route addedRoute;
        if ("none".equals(x.toString())) {
            addedRoute = new Route(-1, name, coordinates, creationDate, to, distance);
        } else {
            addedRoute = new Route(-1, name, coordinates, creationDate, from, to, distance);
        }
        return addedRoute;
    }

}
