package com.lms.services.impl.user;

import com.lms.entities.user.User;
import com.lms.pojos.user.UserPojo;
import com.lms.repositories.user.UserRepository;
import com.lms.services.impl.authority.AccessPrivilegeServiceImpl;
import com.lms.services.impl.authority.AuthorityServiceImpl;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AccessPrivilegeService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by umit.kas on 11.01.2018.
 */
@Service
public class UserServiceImpl implements UserService {

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


    @Override
    public User pojoToEntity(UserPojo pojo) throws Exception {
        return null;
    }

    /**
     * Converts User entity to user pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     *
     * @author umit.kas
     * @param user, authority, ownedCourses, registeredCoursesAsStudent
     * @return UserPojo
     *
     */
    @Override
    public UserPojo entityToPojo(User user, boolean authority, boolean ownedCourses, boolean registeredCoursesAsStudent) throws Exception{
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


    /**
     *
     * Returns authenticated user informations with the access privileges
     *
     * @author umit.kas
     * @param
     * @return UserPojo
     *
     */
    @Override
    public UserPojo getMe() throws Exception{
        UserPojo pojo = new UserPojo();
        User user = customUserDetailService.getAuthenticatedUser();
        if (user != null){
            pojo = entityToPojo(user, true, false, false);
            pojo.setAccessPrivileges(accessPrivilegeService.getAuthenticatedUserAccessPrivileges());
        }

        return pojo;
    }


    @Override
    public List<UserPojo> getAllByVisible(boolean visible) throws Exception{
        List<User> entities = userRepository.findAllByVisible(true);

        List<UserPojo> pojos = new ArrayList<>();

        if (entities != null){

            for (User user : entities){
                pojos.add(entityToPojo(user, true, false, false));
            }
        }
        return pojos;
    }
}
