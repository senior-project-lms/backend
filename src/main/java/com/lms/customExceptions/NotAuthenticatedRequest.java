package com.lms.customExceptions;

public class NotAuthenticatedRequest extends Exception {

    public NotAuthenticatedRequest(String s) {
        super(s);
    }

    public NotAuthenticatedRequest(String s, Throwable throwable) {
        super(s, throwable);
    }
}
