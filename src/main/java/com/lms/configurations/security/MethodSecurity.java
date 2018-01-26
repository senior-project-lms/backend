package com.lms.configurations.security;

import com.lms.entities.AccessPrivilege;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.entities.UserCoursePrivilege;
import com.lms.entities.course.Course;
import com.lms.properties.Privileges;
import com.lms.repositories.AccessPrivilegeRepository;
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
    private AccessPrivilegeRepository accessPrivilegeRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserCoursePrivilegeRepository userCoursePrivilegeRepository;


    /**
     *
     * finds authenticated user, and finds course by publicKey
     * finds the UserCoursePrivilege entity by user and course
     * then checks the parameter privilege is in the UserCoursePrivilege.
     * which means has course privilege or not
     *
     * @author umit.kas
     * @param coursePublicKey, privelege
     * @return boolean
     *
     */
    public boolean hasCoursePrivilege(String coursePublicKey, Privileges privelege){
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
            return userCoursePrivilege.getPrivileges().parallelStream().filter(p -> p.getCode() == privelege.CODE).findAny().isPresent();


//            for (UserCoursePrivilege userCoursePrivilege: course.getUserCoursePrivileges()) {
//
//                if (userCoursePrivilege.getUser().equals(user)){
//                    return userCoursePrivilege.getPrivileges().parallelStream().filter(p -> p.getCode() == privelege.CODE).findAny().isPresent();
////                    for (Privilege privilege : userCoursePrivilege.getPrivileges()){
////                        if (privilege.getName().toString().matches(priveleges.toString())){
////                            return true;
////                        }
////                    }
//
////                    return  false;
//                }
//            }
//            return false;

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }



    }



    /**
     *
     * finds authenticated user entity, and finds privilege entity by parameter privilege
     * then finds AccessPrivilege entity by user and privilege entity
     * if the AccessPrivilege there is a not null entity which means has permission
     * @author umit.kas
     * @param privilege
     * @return boolean
     *
     */
    public boolean hasAccessPrivilege(Privileges privilege){
        try {
                User user = customUserDetailService.getAuthenticatedUser();
                Privilege p = privilegeRepository.findByCode(privilege.CODE);
                if (user != null && p != null){
                    AccessPrivilege accessPrivilege = accessPrivilegeRepository.findByUserAndPrivilegesIn(user, p);
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
