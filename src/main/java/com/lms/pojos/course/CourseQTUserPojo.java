package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseQTUserPojo extends BasePojo {

    private UserPojo user;

    private CoursePojo course;

    private CourseQuizTestPojo qt;


    private Date startedAt;

    private boolean started;

    private boolean finished;

    private Date finishedAt;

    private Date finishesAt;

    private Date currentTime;

    private List<CourseQTUserAnswerPojo> answers;
}
