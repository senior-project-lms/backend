package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class GradeTypePojo extends BasePojo{

    private String name;

    private  float weight;


    private List<GradePojo> grades;


    private  CoursePojo course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public List<GradePojo> getGrades() {
        return grades;
    }

    public void setGrades(List<GradePojo> grades) {
        this.grades = grades;
    }

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }
}
