package ru.itmo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.itmo.server.database.DatabaseConnector;
import ru.itmo.server.mainClasses.Invoker;
import ru.itmo.server.utils.PropertiesManager;

import java.io.IOException;
import java.sql.SQLException;

public class Manager {
    private static final int SERVER_PORT =
            Integer.parseInt(PropertiesManager.getInstance().getProperties().getProperty("port"));
    private static final Logger logger = LoggerFactory.getLogger(Manager.class);

    public static void main(String[] args) throws IOException, SQLException {
        logger.info("Starting Server...");
        Invoker.getInstance().initializeCommands();
        DatabaseConnector.getInstance().initializeDatabase();
        logger.info("Waiting new receive...");
        new Server(SERVER_PORT).start();
    }
}
