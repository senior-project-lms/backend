package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseAssignmentPojo extends BasePojo {

    private String title;

    private CoursePojo course;

    private List<CourseResourcePojo> resources;

    private String content;

    private List<String> resourceKeys;

    private Date dueDate;

    private Date lastDate;

    private boolean gradable;

    private GradePojo grade;

    private boolean published;

    private boolean dueUp;

    private List<StudentAssignmentPojo> studentAssignments;

    private StudentAssignmentPojo studentAssignment;
}
