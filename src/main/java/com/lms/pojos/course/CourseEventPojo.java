package com.lms.pojos.course;
import lombok.Data;

import java.util.Date;

@Data
public class CourseEventPojo {

    private String publicKey;

    private String title;

    private Date start;

    private Date end;

    private CoursePojo course;

    private CourseAnnouncementPojo announcement;
}
