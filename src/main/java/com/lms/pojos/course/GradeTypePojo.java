package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeTypePojo extends BasePojo{

    private String name;

    private  float weight;


    private List<GradePojo> grades;


    private  CoursePojo course;

}
