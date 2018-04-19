package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseQTAnswerPojo extends BasePojo {

    private String text;

    private int type;

    private boolean correct;

    private CourseQTQuestionPojo question;

}
