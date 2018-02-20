package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.enums.EAnswerChoice;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QtAvailableAnswerPojo {

    private String text;

    private EAnswerChoice type;

    private boolean correct;

    private QtQuestionPojo question;

}
