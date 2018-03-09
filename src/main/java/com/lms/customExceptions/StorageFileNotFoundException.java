package com.lms.customExceptions;

public class StorageFileNotFoundException extends RuntimeException{


    private static final long serialVersionUID = -3587958639408886648L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
