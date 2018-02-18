package com.lms.pojos.course;

import com.lms.enums.EAnswerChoice;
import lombok.Data;

@Data
public class QtAvailableAnswerPojo {

    private String text;

    private EAnswerChoice type;

    private boolean correct;

    private QtQuestionPojo question;

}
