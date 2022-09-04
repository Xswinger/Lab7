package ru.itmo.server.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    private static PropertiesManager instance;

    public static PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    private final Properties PROPERTIES = new Properties();

    public Properties getProperties() {
        try {
            PROPERTIES.load(FileUtil.filePath("data.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return PROPERTIES;
    }
}
