package com.lms.pojos.course;

import lombok.Data;

import java.util.Date;

@Data
public class EventPojo {

    private String publicKey;

    private String title;

    private Date start;

    private Date end;
}
