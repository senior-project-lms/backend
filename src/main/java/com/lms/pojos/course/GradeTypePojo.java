package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
public class GradeTypePojo extends BasePojo{

    private String name;

    private  float weight;


    private List<GradePojo> grades;


    private  CoursePojo course;

}
