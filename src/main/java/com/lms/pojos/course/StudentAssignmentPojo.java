package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentAssignmentPojo extends BasePojo {

    private String content;

    private CourseAssignmentPojo assignment;

    private List<CourseResourcePojo> resources;

    private List<String> resourceKeys;


}
