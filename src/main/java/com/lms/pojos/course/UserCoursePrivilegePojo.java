package com.lms.pojos.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.entities.BaseEntity;
import com.lms.pojos.PrivilegePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCoursePrivilegePojo extends BaseEntity{


    private UserPojo user;


    private CoursePojo course;

    private List<PrivilegePojo> privileges;

}
