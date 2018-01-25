package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class QaQuestionPojo extends BasePojo{
    private String title;

    private String content;

    private CoursePojo course;

    private List<QaAnswerPojo> answers;

    private boolean anonymous;

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

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }

    public List<QaAnswerPojo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QaAnswerPojo> answers) {
        this.answers = answers;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}
