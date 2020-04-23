package com.project.logging;

public class InfoLogger extends AbstractLogger {

    public InfoLogger() {
        this.level = AbstractLogger.INFO;
    }


    @Override
    protected void printMsg(String msg) {
        System.out.println("[ INFO ] : " + msg);
    }
}