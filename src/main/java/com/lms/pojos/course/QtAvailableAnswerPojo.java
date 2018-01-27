package com.lms.pojos.course;

import com.lms.enums.AnswerChoice;
import lombok.Data;

@Data
public class QtAvailableAnswerPojo {

    private String text;

    private AnswerChoice type;

    private boolean correct;

    private QtQuestionPojo question;

}
