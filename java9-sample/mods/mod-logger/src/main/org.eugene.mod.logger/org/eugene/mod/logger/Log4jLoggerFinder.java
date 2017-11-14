package org.eugene.mod.logger;

import java.lang.System.LoggerFinder;

public class Log4jLoggerFinder extends LoggerFinder {
    private final Log4jLogger logger = new Log4jLogger();

    @Override
    public System.Logger getLogger(String name, Module module) {
        System.out.printf("Log4jLoggerFinder.getLogger(): [name=%s, module=%s]%n", name, module.getName());
        return logger;
    }
}
