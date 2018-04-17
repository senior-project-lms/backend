package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseQuizTestPojo extends BasePojo {


    private String name;

    private String detail;

    private CoursePojo course;

    private List<CourseQTQuestionPojo> questions;

    private Date startAt;

    private Date endAt;


    private boolean limitedDuration;

    private boolean hasDueDate;

    private boolean gradable;

    private boolean published;

}
