package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import com.lms.pojos.SystemAnnouncementPojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
