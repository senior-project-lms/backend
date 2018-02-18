package com.lms.components;

import com.lms.entities.User;
import com.lms.entities.UserCoursePrivilege;
import com.lms.entities.course.Course;
import com.lms.enums.EPrivilege;
import com.lms.repositories.CourseRepository;
import com.lms.repositories.PrivilegeRepository;
import com.lms.repositories.UserCoursePrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by umit.kas on 29.11.2017.
 */
@Component("methodSecurity")
public class MethodSecurity {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserCoursePrivilegeRepository userCoursePrivilegeRepository;


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
    public boolean hasCoursePrivilege(String coursePublicKey, EPrivilege EPrivilege) {
        try {
            User user = customUserDetailService.getAuthenticatedUser();

            Course course = courseRepository.findByPublicKey(coursePublicKey);



            if (user == null || course == null){
                return false;
            }

            UserCoursePrivilege userCoursePrivilege = userCoursePrivilegeRepository.findByCourseAndUser(course, user);

            if (userCoursePrivilege == null){
                return false;
            }
            return userCoursePrivilege.getPrivileges().parallelStream().filter(p -> p.getCode() == EPrivilege.CODE).findAny().isPresent();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
