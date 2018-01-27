package com.lms.customExceptions;


public class DataNotFoundException extends Exception {


    private static final long serialVersionUID = -103455707237982068L;

    public DataNotFoundException(String s) {
        super(s);
    }

    public DataNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
