package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.User;
import com.lms.enums.ExceptionType;
import com.lms.pojos.UserPojo;
import com.lms.repositories.UserRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AccessPrivilegeService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private CustomUserDetailService userDetailsService;


    @Override
    public boolean userAlreadyExist(String user) {
        return false;
    }

    @Override
    public User pojoToEntity(UserPojo pojo) {
        User entity = new User();

        entity.setUsername(pojo.getUsername());
        entity.setEmail(pojo.getEmail());
        entity.setPassword(pojo.getPassword());
        entity.setSurname(pojo.getSurname());
        entity.setName(pojo.getName());
        entity.setPublicKey(pojo.getPublicKey());

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
    public UserPojo entityToPojo(User user) {

        UserPojo pojo = new UserPojo();
        pojo.setPublicKey(user.getPublicKey());
        pojo.setUsername(user.getUsername());
        pojo.setEmail(user.getEmail());
        pojo.setName(user.getName());
        pojo.setSurname(user.getSurname());

        return pojo;
    }



    /**
     * Returns authenticated user informations with the access privileges
     *
     *
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo getMe() throws DataNotFoundException {
        UserPojo pojo;
        User user = customUserDetailService.getAuthenticatedUser();

        if (user == null){
            throw new DataNotFoundException("No such a user profile is found");
        }

        pojo = entityToPojo(user);
        pojo.setAccessPrivileges(accessPrivilegeService.getAuthenticatedUserAccessPrivileges());
        return pojo;
    }


    @Override
    public List<UserPojo> getAllByVisible(boolean visible) throws DataNotFoundException {
        List<UserPojo> pojos;

        List<User> entities = userRepository.findAllByVisible(visible);

        if (entities == null){
            throw new DataNotFoundException("No such a User collection is found");
        }

        pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());


        pojos.stream().forEach(pojo -> pojo.setVisible(visible));


        return pojos;
    }

    @Override
    public UserPojo getByPublicKey(String publicKey) throws DataNotFoundException {
        UserPojo pojo;
        User entity = userRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a user is found for publicKey: %s", publicKey));
        }
        pojo = entityToPojo(entity);
        return pojo;
    }

    @Override
    public boolean save(UserPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        User entity = pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        Authority authorityEntity = authorityService.findByPublicKey(pojo.getAuthority().getPublicKey());


        entity.setAuthority(authorityEntity);
        entity.setCreatedBy(authenticatedUser);

        entity = userRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException(String.format("No such user is saved by email: %s", pojo.getEmail()));
        }

        return true;
    }

    @Override
    public boolean save(List<UserPojo> pojos) throws ExecutionFailException, DataNotFoundException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        List<User> entities = new ArrayList<>();

        for (UserPojo pojo : pojos) {

            User entity = pojoToEntity(pojo);
            entity.generatePublicKey();
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));

            if (entity.getAuthority() != null) {
                Authority authorityEntity = authorityService.findByPublicKey(pojo.getAuthority().getPublicKey());

                entity.setAuthority(authorityEntity);

                entity.setCreatedBy(authenticatedUser);
                entities.add(entity);
            }
        }

        entities = userRepository.save(entities);
        if (entities == null || entities.size() == 0){
            throw new ExecutionFailException("No such a user collection is saved");
        }
        return true;

    }

    @Override
    public User findByEmail(String email) throws DataNotFoundException {
        User entity = userRepository.findByEmail(email);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a user found by email: %s", email));
        }

        return entity;
    }

    @Override
    public boolean updateVisibility(String publicKey, boolean visible) throws DataNotFoundException {
        User updatedBy = userDetailsService.getAuthenticatedUser();

        if (updatedBy == null) {
            throw new SecurityException("Authenticated User is not found");
        };
        User entity=userRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException(String.format("There is no user by publicKey: %s", publicKey));

        }
        entity.setUpdatedBy(updatedBy);

        entity.setVisible(visible);
        entity=userRepository.save(entity);
        if (entity == null || entity.getId() == 0) {
            throw new DataNotFoundException("Setting user visibility is not executed successfully");
        }

        return true;
    }

    @Override
    public Map<String, Integer> getUserStatus() {
        HashMap <String, Integer> status=new HashMap<>();
        int visibleUsers=userRepository.countByVisible(true);
        int invisibleUsers=userRepository.countByVisible(false);

        status.put("visibleUsers",visibleUsers);
        status.put("invisibleUsers",invisibleUsers);

        return status;

    }

}
