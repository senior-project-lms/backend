package com.lms.pojos;

import lombok.Data;

@Data

public class GlobalQaAnswerPojo extends BasePojo {

    private String content;

    private GlobalQaQuestionPojo question;

}
