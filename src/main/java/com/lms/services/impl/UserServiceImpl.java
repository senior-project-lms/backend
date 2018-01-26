package com.lms.services.impl;

import com.lms.entities.Authority;
import com.lms.entities.User;
import com.lms.pojos.UserPojo;
import com.lms.repositories.UserRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AccessPrivilegeService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        User entity = new User();

        entity.setUsername(pojo.getUsername());
        entity.setEmail(pojo.getEmail());
        entity.setPassword(pojo.getPassword());
        entity.setSurname(pojo.getSurname());
        entity.setName(pojo.getName());
        entity.setPublicKey(pojo.getPublicKey());
        if (pojo.getAuthority() != null) {
            entity.setAuthority(authorityService.pojoToEntity(pojo.getAuthority()));
        }
        //do not forget to convert other entities to pojos

        return entity;
    }


    /**
     * Converts User entity to user pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param user, authority, ownedCourses, registeredCoursesAsStudent
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo entityToPojo(User user, boolean authority, boolean ownedCourses, boolean registeredCoursesAsStudent) throws Exception {
        UserPojo pojo = new UserPojo();
        if (user != null) {
            pojo.setUsername(user.getUsername());
            pojo.setEmail(user.getEmail());
            pojo.setName(user.getName());
            pojo.setSurname(user.getSurname());

            if (authority) {
                pojo.setAuthority(authorityService.entityToPojo(user.getAuthority()));
            }

            // fill the empty if blocks as directive of the backend document
            if (ownedCourses) {

            }
            if (registeredCoursesAsStudent) {

            }

            return pojo;

        }
        return null;
    }


    /**
     * Returns authenticated user informations with the access privileges
     *
     * @param
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo getMe() throws Exception {
        UserPojo pojo = new UserPojo();
        User user = customUserDetailService.getAuthenticatedUser();
        if (user != null) {
            pojo = entityToPojo(user, true, false, false);
            pojo.setAccessPrivileges(accessPrivilegeService.getAuthenticatedUserAccessPrivileges());
        }

        return pojo;
    }


    @Override
    public List<UserPojo> getAllByVisible(boolean visible) throws Exception {
        List<User> entities = userRepository.findAllByVisible(true);

        List<UserPojo> pojos = new ArrayList<>();

        if (entities != null) {

            for (User user : entities) {
                pojos.add(entityToPojo(user, true, false, false));
            }
        }
        return pojos;
    }

    @Override
    public UserPojo getUser(String publicKey) throws Exception {
        UserPojo pojo = new UserPojo();
        User entity = userRepository.findByPublicKey(publicKey);
        if (entity != null) {
            pojo = entityToPojo(entity, true, false, false);
        }
        return pojo;
    }

    @Override
    public boolean save(UserPojo pojo) throws Exception {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        User entity = pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        if (entity.getAuthority() != null) {
            Authority authorityEntity = authorityService.getAuthorityByAccessLevel(entity.getAuthority().getAccessLevel());
            entity.setAuthority(authorityEntity);
            entity.setCreatedBy(authenticatedUser);
            return userRepository.save(entity).getId() > 0;


        }
        return false;
    }

    @Override
    public boolean save(List<UserPojo> pojos) throws Exception {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        List<User> entities = new ArrayList<>();

        for (UserPojo pojo : pojos) {

            User entity = pojoToEntity(pojo);
            entity.generatePublicKey();
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));

            if (entity.getAuthority() != null) {
                Authority authorityEntity = authorityService.getAuthorityByAccessLevel(entity.getAuthority().getAccessLevel());
                entity.setAuthority(authorityEntity);

                entity.setCreatedBy(authenticatedUser);
                entities.add(entity);
            }
        }

        userRepository.save(entities);
        return true;

    }


}
