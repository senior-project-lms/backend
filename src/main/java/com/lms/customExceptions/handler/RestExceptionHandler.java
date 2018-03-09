package com.lms.customExceptions.handler;


import com.lms.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class RestExceptionHandler{


    @ExceptionHandler(value = {NotAuthenticatedRequest.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiError handleNotAuthenticatedFunctionalityException(NotAuthenticatedRequest ex) {
        return new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }


    @ExceptionHandler(value = {EmptyFieldException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleEmptyFieldException(EmptyFieldException ex){
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }



    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleDataNotFoundException(DataNotFoundException ex){
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(value = {ExecutionFailException.class})
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public ApiError handleNoSuchProcessExecutedException(ExecutionFailException ex){
        return new ApiError(HttpStatus.GONE, ex.getMessage());
    }


    @ExceptionHandler(value = {ExistRecordException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleNoSuchProcessExecutedException(ExistRecordException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }



}
