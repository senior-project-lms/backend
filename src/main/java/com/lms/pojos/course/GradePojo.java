package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class GradePojo extends BasePojo{


    private String name;

    private List<ScorePojo> scores;

    private GradeTypePojo gradeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScorePojo> getScores() {
        return scores;
    }

    public void setScores(List<ScorePojo> scores) {
        this.scores = scores;
    }

    public GradeTypePojo getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeTypePojo gradeType) {
        this.gradeType = gradeType;
    }
}
