package com.lms.pojos.course;

import com.lms.properties.AnswerChoice;

public class QtAvailableAnswerPojo {

    private String text;

    private AnswerChoice type;

    private boolean correct;

    private QtQuestionPojo question;

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

    public QtQuestionPojo getQuestion() {
        return question;
    }

    public void setQuestion(QtQuestionPojo question) {
        this.question = question;
    }
}
