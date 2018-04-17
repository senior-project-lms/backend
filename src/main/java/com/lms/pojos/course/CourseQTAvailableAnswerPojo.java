package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseQTAvailableAnswerPojo {

    private String text;

    private int type;

    private boolean correct;

    private CourseQTQuestionPojo question;

}
