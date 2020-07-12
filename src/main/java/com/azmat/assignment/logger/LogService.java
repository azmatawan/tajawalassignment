package com.azmat.assignment.logger;

import org.apache.log4j.Logger;

public class LogService implements LoggerInterface {

    private Logger logger;

    public LogService(Logger logger) {
        this.logger = logger;
    }

    public void logAsInfo(Object... args) {
        try {
            StringBuilder builder = new StringBuilder();
            for (Object o : args)
                builder.append(o + "");
            logger.info(builder);
        } catch (Throwable t) {
            logger.error("Exception occurred while logging", t);
        }
    }

}
