package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentPojo extends BasePojo {

    private String name;

    private CoursePojo course;


    private List<CourseResourcePojo> courseResources;

    private String content;
}