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

    private List<UserScorePojo> userScores;

    private CoursePojo course;

    private double average;

    private double score;

    private boolean menu = true;

    private boolean published;


    public double getWeightedScore(){
        if (maxScore != 100){
            double rate = score / maxScore;
            return score * rate * (weight / 100);
        }
        return score * (weight / 100);
    }

    public double getWeightedAverage(){
        if (maxScore != 100){
            double rate = average / maxScore;
            return average * rate * (weight / 100);
        }

        return average * (weight / 100);
    }


    public double getWeighedMaxScore(){

        return  maxScore * (weight / 100);
    }
}
