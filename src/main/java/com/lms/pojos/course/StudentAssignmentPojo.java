package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentAssignmentPojo {

    private String content;

    private CourseAssignmentPojo assignment;

    private List<CourseResourcePojo> courseResources;
}
