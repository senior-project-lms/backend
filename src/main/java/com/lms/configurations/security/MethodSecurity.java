package com.lms.configurations.security;

import com.lms.entities.Course;
import com.lms.entities.User;
import com.lms.entities.UserCoursePrivilege;
import com.lms.properties.Privileges;
import com.lms.repositories.CourseRepositoy;
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
    private CourseRepositoy courseRepositoy;

    public boolean hasCoursePrivilege(String coursePublicKey, Privileges priveleges){
        try {
            User user = customUserDetailService.getAuthenticatedUser();

            Course course = this.courseRepositoy.findByPublicKey(coursePublicKey);

            if (user == null || course == null){
                return false;
            }

            for (UserCoursePrivilege userCoursePrivilege: course.getUserCoursePrivileges()) {

                if (userCoursePrivilege.getUser().equals(user)){
                    return userCoursePrivilege.getPrivileges().parallelStream().filter(privilege -> privilege.getName().matches(priveleges.toString())).findAny().isPresent();
//                    for (Privilege privilege : userCoursePrivilege.getPrivileges()){
//                        if (privilege.getName().toString().matches(priveleges.toString())){
//                            return true;
//                        }
//                    }

//                    return  false;
                }
            }
            return false;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }



    }
}
