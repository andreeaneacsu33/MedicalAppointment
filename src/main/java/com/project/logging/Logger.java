package com.project.logging;

public class Logger {

    public static AbstractLogger getLogger(){
        AbstractLogger errorLogger  = new ErrorLogger();
        AbstractLogger debugLogger = new DebugLogger();
        AbstractLogger infoLogger = new InfoLogger();
        errorLogger.setNextLogger(debugLogger);
        debugLogger.setNextLogger(infoLogger);
        return errorLogger;
    }


}
