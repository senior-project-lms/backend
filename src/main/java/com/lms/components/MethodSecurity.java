package com.lms.components;

import com.lms.entities.User;
import com.lms.entities.course.UserCoursePrivilege;
import com.lms.entities.course.Course;
import com.lms.enums.ECoursePrivilege;
import com.lms.enums.EPrivilege;
import com.lms.repositories.CourseRepository;
import com.lms.repositories.PrivilegeRepository;
import com.lms.repositories.UserCoursePrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by umit.kas on 29.11.2017.
 */
@Component("methodSecurity")
public class MethodSecurity {


    @Autowired
    UserCoursePrivilegeService userCoursePrivilegeService;

    /**
     *
     * finds authenticated user, and finds course by publicKey
     * finds the UserCoursePrivilege entity by user and course
     * then checks the parameter EPrivilege is in the UserCoursePrivilege.
     * which means has course EPrivilege or not
     *
     * @author umit.kas
     * @param coursePublicKey, EPrivilege
     * @return boolean
     *
     */
    public boolean hasCoursePrivilege(String coursePublicKey, ECoursePrivilege privilege) {
        try {
            return userCoursePrivilegeService.hasPrivilege(coursePublicKey, privilege);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
