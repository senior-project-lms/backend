package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseQTQuestionPojo extends BasePojo {

    private int order;

    private String content;

    private List<CourseQTAvailableAnswerPojo> answers;

}
