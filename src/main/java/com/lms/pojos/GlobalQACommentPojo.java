package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.entities.GlobalQAComment;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalQACommentPojo extends BasePojo {

    private String content;

}