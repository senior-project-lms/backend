package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradePojo extends BasePojo{


    private String name;

    private double maxScore;

    private double weight;

    private List<ScorePojo> scores;

    private CoursePojo course;

    private double average;

    private double score;

    private double overAllAverage;

    private double overAllGrade;

    private boolean menu = true;

    private boolean published;


    public double getWeightedScore(){
        return score * (weight / 100);
    }

    public double getWeightedAverage(){
        return average * (weight / 100);
    }
}
