package ru.itmo.server.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.server.exceptions.DatabaseException;
import ru.itmo.server.utils.FileUtil;
import ru.itmo.server.utils.PropertiesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class DatabaseConnector {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
    private static final Connection CONNECTION;

    static {
        try {
            logger.info("Connection to database...");
            final String DATABASE_URL = PropertiesManager.getInstance().getProperties().getProperty("url");
            final String USER = PropertiesManager.getInstance().getProperties().getProperty("name");
            final String PASSWORD = PropertiesManager.getInstance().getProperties().getProperty("password");
            CONNECTION = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            logger.info("Connection to database successfully");
        } catch (SQLException e) {
            logger.warn("Error when try connect to database");
            throw new RuntimeException(e);
        }
    }

    private static DatabaseConnector instance = null;

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Statement createStatement() throws DatabaseException {
        try {
            return CONNECTION.createStatement();
        } catch (SQLException exception) {
            throw new DatabaseException("Cannot create statement:" + Arrays.toString(exception.getStackTrace()));
        }
    }

    public PreparedStatement createPreparedStatement(String url) throws DatabaseException{
        try {
            return CONNECTION.prepareStatement(url);
        } catch (SQLException exception) {
            throw new DatabaseException("Cannot create prepare statement:" + Arrays.toString(exception.getStackTrace()));
        }
    }

    public void initializeDatabase() throws SQLException {
        InputStreamReader script = new InputStreamReader(FileUtil.filePath("scriptDb.sql"));
        BufferedReader bufferedReader = new BufferedReader(script);
        String lines = bufferedReader.lines().collect(Collectors.joining());
        String[] stringList = lines.split(";");
        for (String command : stringList) {
            PreparedStatement preparedStatement = DatabaseConnector.this.createPreparedStatement(command);
            preparedStatement.execute();
        }
    }
}
