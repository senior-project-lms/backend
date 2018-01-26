package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
public class GradePojo extends BasePojo{


    private String name;

    private List<ScorePojo> scores;

    private GradeTypePojo gradeType;

}
