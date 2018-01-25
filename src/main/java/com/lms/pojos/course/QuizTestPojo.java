package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.Date;
import java.util.List;

public class QuizTestPojo extends BasePojo {


    private String name;

    private CoursePojo course;

    private List<QtQuestionPojo> questions;

    private Date startAt;

    private Date endAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }

    public List<QtQuestionPojo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QtQuestionPojo> questions) {
        this.questions = questions;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }
}
