package com.lms.pojos;

import com.lms.entities.SystemAnnouncement;
import com.lms.pojos.course.CoursePojo;
import lombok.Data;

import java.util.Date;

@Data
public class SystemEventPojo {

    private String publicKey;

    private String title;

    private Date start;

    private Date end;

    private SystemAnnouncement announcement;
}
