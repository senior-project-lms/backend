package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseAssignmentPojo extends BasePojo {

    private String name;

    private CoursePojo course;

    private List<CourseResourcePojo> courseResources;

    private String content;

    private GradePojo grade;

    private List<StudentAssignmentPojo> studentAssignments;
}
