package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.properties.AnswerChoice;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_available_answers")
public class QtAvailableAnswer extends BaseEntity {

    private String text;

    private AnswerChoice type;

    private boolean correct;

    @ManyToOne
    private QtQuestion question;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AnswerChoice getType() {
        return type;
    }

    public void setType(AnswerChoice type) {
        this.type = type;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public QtQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QtQuestion question) {
        this.question = question;
    }
}
