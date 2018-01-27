package com.lms.customExceptions;

import com.lms.enums.ExceptionType;
import lombok.Data;

@Data
public class ServiceException extends Exception{


    private static final long serialVersionUID = 2153108352380084204L;

    private ExceptionType type;

    public ServiceException(ExceptionType type, String s) {
        super(s);
        this.type = type;
    }

    public ServiceException(ExceptionType type, String s, Throwable throwable) {
        super(s, throwable);
        this.type = type;
    }
}
