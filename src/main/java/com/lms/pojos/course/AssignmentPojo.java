package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class AssignmentPojo extends BasePojo {

    private String name;

    private CoursePojo course;


    private List<CourseResourcePojo> courseResources;

    private String content;

}
