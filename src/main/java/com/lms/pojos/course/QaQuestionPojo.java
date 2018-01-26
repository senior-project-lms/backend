package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
public class QaQuestionPojo extends BasePojo{
    private String title;

    private String content;

    private CoursePojo course;

    private List<QaAnswerPojo> answers;

    private boolean anonymous;

}
