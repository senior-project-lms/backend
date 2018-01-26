package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.user.UserPojo;
import lombok.Data;

@Data
public class ScorePojo extends BasePojo{


    private GradePojo grade;

    private UserPojo student;

    private float score;

}