package com.project.logging;

public class ErrorLogger extends AbstractLogger {

    public ErrorLogger() {
        this.level = AbstractLogger.ERROR;
    }

    /* (non-Javadoc)
     * @see pattern.structural.chain.Logger#printMsg(java.lang.String)
     */
    @Override
    protected void printMsg(String msg) {
        System.err.println("[ ERROR ] : "+msg);

    }


}