package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.entities.GlobalQA;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQAPojo extends BasePojo{

    private String title;

    private String content;

    private boolean anonymous;

    private List<GlobalQAPojo> answers;

    private List<GlobalQACommentPojo> comments;

    private List<GlobalQATagPojo> tags;

    private boolean answer;

    private boolean owner;

    private long upCount;
    private long downCount;
    private boolean upped;
    private boolean downed;
    private boolean starred;
    private long starCount;
    private long answerCount;

}
