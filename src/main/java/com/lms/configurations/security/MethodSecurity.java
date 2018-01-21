package com.lms.configurations.security;

import com.lms.entities.authority.AccessPrivilege;
import com.lms.entities.authority.Privilege;
import com.lms.entities.course.Course;
import com.lms.entities.user.User;
import com.lms.entities.authority.UserCoursePrivilege;
import com.lms.properties.Privileges;
import com.lms.repositories.authority.AcessPrivilegeRepository;
import com.lms.repositories.course.CourseRepositoy;
import com.lms.repositories.authority.PrivilegeRepository;
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

    @Autowired
    private AcessPrivilegeRepository acessPrivilegeRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;


    public boolean hasCoursePrivilege(String coursePublicKey, Privileges privelege){
        try {
            User user = customUserDetailService.getAuthenticatedUser();

            Course course = this.courseRepositoy.findByPublicKey(coursePublicKey);



            if (user == null || course == null){
                return false;
            }

            for (UserCoursePrivilege userCoursePrivilege: course.getUserCoursePrivileges()) {

                if (userCoursePrivilege.getUser().equals(user)){
                    return userCoursePrivilege.getPrivileges().parallelStream().filter(p -> p.getCode() == privelege.CODE).findAny().isPresent();
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


    public boolean hasAdminPrivilege(Privileges privilege){
        try {
                User user = customUserDetailService.getAuthenticatedUser();
                Privilege p = privilegeRepository.findByCode(privilege.CODE);
                if (user != null && p != null){
                    AccessPrivilege accessPrivilege = acessPrivilegeRepository.findByUserAndPrivilegesIn(user, p);
                    if (accessPrivilege != null){
                        return true;
                    }
                }
                return false;
        }
        catch (Exception e){
            e.printStackTrace();

        }
        return false;
    }
}
