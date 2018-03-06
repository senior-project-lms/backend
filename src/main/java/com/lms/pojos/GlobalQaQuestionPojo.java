package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQaQuestionPojo extends BasePojo{

    private String title;

    private String content;

    private boolean anonymous;

    private List<GlobalQaAnswerPojo> answers;



}
