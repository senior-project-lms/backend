package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.user.UserPojo;

public class ScorePojo extends BasePojo{


    private GradePojo grade;

    private UserPojo student;

    private float score;

    public GradePojo getGrade() {
        return grade;
    }

    public void setGrade(GradePojo grade) {
        this.grade = grade;
    }

    public UserPojo getStudent() {
        return student;
    }

    public void setStudent(UserPojo student) {
        this.student = student;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
