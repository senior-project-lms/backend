package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
public class QtQuestionPojo extends BasePojo {
    private String title;

    private String content;

    private List<QtAvailableAnswerPojo> answers;

}
