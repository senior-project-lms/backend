package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class QtQuestionPojo extends BasePojo {
    private String title;

    private String content;

    private List<QtAvailableAnswerPojo> answers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QtAvailableAnswerPojo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QtAvailableAnswerPojo> answers) {
        this.answers = answers;
    }
}
