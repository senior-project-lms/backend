package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.user.User;

import javax.persistence.*;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Entity
@Table(name = "scores")
public class Score extends BaseEntity {


    @ManyToOne
    private Grade grade;

    @OneToOne
    private User student;

    private float score;

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
