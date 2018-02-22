package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizTestPojo extends BasePojo {


    private String name;

    private CoursePojo course;

    private List<QtQuestionPojo> questions;

    private Date startAt;

    private Date endAt;

}
