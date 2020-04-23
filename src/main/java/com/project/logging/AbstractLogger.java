package com.project.logging;

public abstract class AbstractLogger {

    public final static int INFO=1;
    public final static int DEBUG=2;
    public final static int ERROR=3;

    protected int level;
    private AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void log(int level,String msg){
        if(this.level <= level)
            this.printMsg(msg);
        else if (nextLogger!=null)
            nextLogger.log(level, msg);
    }

    protected abstract void printMsg(String msg);
}