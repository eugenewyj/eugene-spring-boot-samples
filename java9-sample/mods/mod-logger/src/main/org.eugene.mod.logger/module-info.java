module org.eugene.mod.logger {
    requires log4j.api;
    requires log4j.core;
    exports org.eugene.mod.logger;
    provides java.lang.System.LoggerFinder with org.eugene.mod.logger.Log4jLoggerFinder;
}