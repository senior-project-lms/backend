package com.lms.entities.course;

import com.lms.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Entity
@Table(name = "Grades")
public class Grade extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "grade")
    private List<Score> scores;

    @ManyToOne
    private GradeType gradeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }
}
