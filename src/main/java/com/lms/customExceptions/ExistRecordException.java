package com.lms.customExceptions;

public class ExistRecordException extends Exception {

    public ExistRecordException(String s) {
        super(s);
    }

    public ExistRecordException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
