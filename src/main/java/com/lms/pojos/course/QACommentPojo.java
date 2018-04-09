package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.entities.course.QaComment;
import com.lms.pojos.BasePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QaCommentPojo extends BasePojo {



    private String content;

    private boolean anonymous;

}
