package com.lms.entities.course;

import com.lms.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qa_answers")
public class QaAnswer extends BaseEntity {


    private String content;

    @ManyToOne
    private QaQuestion question;

    private boolean verified;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public QaQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QaQuestion question) {
        this.question = question;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
