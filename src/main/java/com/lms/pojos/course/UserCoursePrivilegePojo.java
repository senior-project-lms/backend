package com.lms.pojos.course;

import com.lms.entities.BaseEntity;
import com.lms.pojos.PrivilegePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

import java.util.List;

@Data
public class UserCoursePrivilegePojo extends BaseEntity{


    private UserPojo user;


    private CoursePojo course;

    private List<PrivilegePojo> privileges;

}
