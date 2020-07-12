package com.azmat.assignment.logger;

import org.apache.log4j.Logger;

public class LogFactory {

    private LogFactory() {

    }

    public static LogFactory instance() {
        return InstanceHolder.logFactory;
    }

    public LoggerInterface getLogService(Class<?> className) {
        return new LogService(Logger.getLogger(className));
    }

    private static class InstanceHolder {
        static LogFactory logFactory = new LogFactory();
    }

}
