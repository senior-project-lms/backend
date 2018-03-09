package com.lms.customExceptions;

public class ExecutionFailException extends Exception{

    public ExecutionFailException(String s) {
        super(s);
    }

    public ExecutionFailException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
