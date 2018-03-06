package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQaAnswerPojo extends BasePojo {

    private String content;

    private GlobalQaQuestionPojo question;

}
