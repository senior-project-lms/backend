package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

@Data
public class EnrolmentRequestPojo extends BasePojo {

    private CoursePojo course;

    private UserPojo user;

}


