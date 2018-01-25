package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.user.UserPojo;

public class QaAnswerPojo extends BasePojo {


    private String content;

    private QaQuestionPojo question;

    private boolean verified;

    private UserPojo verifiedBy;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QaQuestionPojo getQuestion() {
        return question;
    }

    public void setQuestion(QaQuestionPojo question) {
        this.question = question;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public UserPojo getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(UserPojo verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
}
