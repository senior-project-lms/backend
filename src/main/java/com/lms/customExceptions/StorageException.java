package com.lms.customExceptions;

public class StorageException extends RuntimeException {


    private static final long serialVersionUID = -7003607003153519920L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);

    }

}
