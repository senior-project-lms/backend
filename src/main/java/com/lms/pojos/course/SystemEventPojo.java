package com.lms.pojos.course;

import com.lms.pojos.SystemAnnouncementPojo;
import lombok.Data;

import java.util.Date;

@Data
public class SystemEventPojo {

    private String publicKey;

    private String title;

    private Date start;

    private Date end;

    private SystemAnnouncementPojo announcement;
}
