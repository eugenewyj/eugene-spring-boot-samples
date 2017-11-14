package org.eugene.mod.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class Log4jLogger implements System.Logger {
    private final Logger logger = LogManager.getLogger();

    @Override
    public String getName() {
        return "Log4jLogger";
    }

    @Override
    public boolean isLoggable(Level level) {
        org.apache.logging.log4j.Level log4jLevel = toLog4jLevel(level);
        return logger.isEnabled(log4jLevel);
    }

    private org.apache.logging.log4j.Level toLog4jLevel(Level level) {
        switch (level) {
            case ALL:
                return org.apache.logging.log4j.Level.ALL;
            case DEBUG:
                return org.apache.logging.log4j.Level.DEBUG;
            case ERROR:
                return org.apache.logging.log4j.Level.ERROR;
            case INFO:
                return org.apache.logging.log4j.Level.INFO;
            case OFF:
                return org.apache.logging.log4j.Level.OFF;
            case TRACE:
                return org.apache.logging.log4j.Level.TRACE;
            case WARNING:
                return org.apache.logging.log4j.Level.WARN;
            default:
                throw new RuntimeException("不知道的级别：" + level);
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        logger.log(toLog4jLevel(level), msg, thrown);

    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        logger.printf(toLog4jLevel(level), format, params);
    }
}
