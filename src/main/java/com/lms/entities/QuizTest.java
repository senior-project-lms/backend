package com.lms.entities;

import org.omg.CORBA.PRIVATE_MEMBER;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "course_quiz_tests")
public class QuizTest extends BaseEntity{

    private String name;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<QtQuestion> questions;

    private Date startAt;

    private Date endAt;

    public List<QtQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QtQuestion> questions) {
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
