package com.project.logging;

public class DebugLogger extends AbstractLogger {

    public DebugLogger() {
        this.level = AbstractLogger.DEBUG;
    }

    /* (non-Javadoc)
     * @see pattern.structural.chain.Logger#printMsg(java.lang.String)
     */
    @Override
    protected void printMsg(String msg) {
        System.out.println("[ DEBUG ] : "+msg);
    }

}