package com.lms.components;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.customExceptions.ServiceException;
import com.lms.enums.ExceptionType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionConverter{

    public void convert(ServiceException ex) throws ExecutionFailException, DataNotFoundException, ExistRecordException {

        if (ex.getType().equals(ExceptionType.NO_SUCH_DATA_NOT_FOUND)){
            throw new DataNotFoundException(ex.getMessage());
        }
        else if (ex.getType().equals(ExceptionType.EXECUTION_FAILS)){
            throw new ExecutionFailException(ex.getMessage());
        } else if (ex.getType().equals(ExceptionType.DATA_ALREADY_EXIST)) {
            throw new ExistRecordException(ex.getMessage());
        }
    }

}
