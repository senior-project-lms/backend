package com.lms.customExceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class RestExceptionHandler{



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
        return new ApiError(HttpStatus.NOT_IMPLEMENTED  , ex.getMessage());
    }


    @ExceptionHandler(value = {ExecutionFailException.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    public ApiError handleNoSuchProcessExecutedException(ExecutionFailException ex){
        return new ApiError(HttpStatus.NOT_IMPLEMENTED  , ex.getMessage());
    }




}
