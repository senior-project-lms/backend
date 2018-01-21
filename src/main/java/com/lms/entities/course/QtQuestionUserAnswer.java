package com.lms.entities.course;

import com.lms.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_question_user_answers")
public class QtQuestionUserAnswer extends BaseEntity {

    @OneToOne
    private QtQuestion question;

    @OneToOne
    private  QtAvailableAnswers answer;


    public QtQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QtQuestion question) {
        this.question = question;
    }

    public QtAvailableAnswers getAnswer() {
        return answer;
    }

    public void setAnswer(QtAvailableAnswers answer) {
        this.answer = answer;
    }
}
