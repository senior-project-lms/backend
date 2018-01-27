package com.lms.customExceptions;


public class EmptyFieldException extends Exception {


    private static final long serialVersionUID = -103455707237982068L;

    public EmptyFieldException(String s) {
        super(s);
    }

    public EmptyFieldException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
