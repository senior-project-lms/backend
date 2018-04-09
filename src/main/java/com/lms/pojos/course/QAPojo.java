package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QAPojo extends BasePojo {

    private String title;

    private String content;

    private boolean anonymous;

    private List<QAPojo> answers;

    private List<QACommentPojo> comments;

    private CoursePojo course;

    private boolean answer;

    private long upCount;
    private long downCount;
    private boolean upped;
    private boolean downed;
    private boolean starred;
    private long starCount;
    private long answerCount;
}
