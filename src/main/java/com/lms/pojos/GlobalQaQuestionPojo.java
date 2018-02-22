package com.lms.pojos;

import lombok.Data;

import java.util.List;

@Data
public class GlobalQaQuestionPojo extends BasePojo{

    private String title;

    private String content;

    private boolean anonymous;

    private List<GlobalQaAnswerPojo> answers;



}
