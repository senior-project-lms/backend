package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.entities.GlobalQaAnswer;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQaAnswerPojo extends BasePojo {

    private String content;

    private GlobalQaQuestionPojo question;

    private List<GlobalQaAnswer> subanswers;

}