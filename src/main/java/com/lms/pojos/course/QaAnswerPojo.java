package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

@Data
public class QaAnswerPojo extends BasePojo {


    private String content;

    private QaQuestionPojo question;

    private boolean verified;

    private UserPojo verifiedBy;

}
