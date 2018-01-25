package com.lms.pojos.course;

import com.lms.entities.BaseEntity;
import com.lms.pojos.authority.PrivilegePojo;
import com.lms.pojos.user.UserPojo;

import java.util.List;

public class UserCoursePrivilegePojo extends BaseEntity{


    private UserPojo user;


    private CoursePojo course;

    private List<PrivilegePojo> privileges;

    public UserPojo getUser() {
        return user;
    }

    public void setUser(UserPojo user) {
        this.user = user;
    }

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }

    public List<PrivilegePojo> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegePojo> privileges) {
        this.privileges = privileges;
    }
}
