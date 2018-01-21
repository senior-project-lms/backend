package com.lms.services.impl.user;

import com.lms.entities.user.User;
import com.lms.pojos.user.UserPojo;
import com.lms.repositories.user.UserRepository;
import com.lms.services.impl.authority.AccessPrivilegeService;
import com.lms.services.impl.authority.AuthorityService;
import com.lms.services.custom.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by umit.kas on 11.01.2018.
 */
@Service
public class UserService {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccessPrivilegeService accessPrivilegeService;

    public UserPojo entityToPojo(User user, boolean authority, boolean ownedCourses, boolean registeredCoursesAsStudent){
        UserPojo pojo = new UserPojo();
        if (user != null){
            pojo.setUsername(user.getUsername());
            pojo.setEmail(user.getEmail());
            pojo.setName(user.getName());
            pojo.setSurname(user.getSurname());

            if (authority) {
                pojo.setAuthority(authorityService.entityToPojo(user.getAuthority()));
            }

            // fill the empty if blocks as directive of the backend document
            if(ownedCourses){

            }
            if (registeredCoursesAsStudent){

            }

            return pojo;

        }
        return null;
    }


    public UserPojo getMe() throws Exception{
        UserPojo pojo = new UserPojo();
        User user = customUserDetailService.getAuthenticatedUser();
        if (user != null){
            pojo = entityToPojo(user, true, false, false);
            pojo.setAccessPrivileges(accessPrivilegeService.getPrivileges());
        }

        return pojo;
    }




}
