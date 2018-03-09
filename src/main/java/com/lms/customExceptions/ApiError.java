package com.lms.customExceptions;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
public class ApiError{

    private String id;
    private int status;
    @CreatedDate
    private long timestamp;
    private String message;



    public ApiError(HttpStatus status, String message) {
        this.timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.status = status.value();
    }
}
