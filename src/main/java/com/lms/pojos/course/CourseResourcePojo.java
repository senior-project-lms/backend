package com.lms.pojos.course;

import com.lms.entities.course.ResourcePackagePojo;
import com.lms.pojos.BasePojo;
import com.lms.pojos.global.SystemAnnouncementPojo;
import com.lms.pojos.user.UserPojo;

import java.util.Date;

public class CourseResourcePojo extends BasePojo {


    private String name;

    private String path;

    private String type;

    private CoursePojo course;

    private ResourcePackagePojo resourcePackage;

    private SystemAnnouncementPojo announcement;

    private Date deletedAt;


    private UserPojo deletedBy;
}
