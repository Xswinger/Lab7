package ru.itmo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itmo.common.dto.ExampleDto;

public class Manager {
    private static final Logger logger = LoggerFactory.getLogger(Manager.class);

    public static void main(String[] args) {
        ExampleDto exampleDto = new ExampleDto();
        logger.info("Hello, I'm server");
    }
}
