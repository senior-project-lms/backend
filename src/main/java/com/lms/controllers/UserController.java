package com.lms.controllers;

import com.lms.customExceptions.*;
import com.lms.pojos.UserPojo;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.UserService;
import org.aspectj.apache.bcel.generic.RET;
import org.hibernate.engine.spi.ExceptionConverter;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class UserController {


    @Autowired
    private UserService userService;



    @GetMapping(value = {"/me"})
    public UserPojo getMe() throws DataNotFoundException {

        return userService.getMe();


    }

    /**
     * the pojo object that comes from API goes to save method of the Service
     * check userPojo is whether null or not
     * check the pojo which includes email, name, password, username and authority if one of them is null or empty
     * return false, else return result of the save method of the service
     *
     * @param userPojo
     * @return boolean
     * @author atalay samet ergen
     */
    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).SAVE_USER.CODE)")
    @PostMapping(value = {"/user"})
    public boolean saveUser(@RequestBody UserPojo userPojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {


        if (isValidUserPojo(userPojo)) {
            return userService.save(userPojo);
        }

        return false;

    }

    /**
     * the pojo object list that comes from API goes to save method of the Service
     * check userPojo list is whether null or not
     * check the pojo list which includes emails, names, passwords, usernames and authorities and if one of them is null or empty
     * return false, else return result of the save method of the service
     *
     * @param userPojos
     * @return boolean
     * @author atalay samet ergen
     */
    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).SAVE_USER.CODE)")
    @PostMapping(value = {"/users"})
    public boolean saveUsers(@RequestBody List<UserPojo> userPojos) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {

        for (UserPojo pojo : userPojos) {
            if (!isValidUserPojo(pojo)) {
                throw new ExecutionFailException("No such user is saved");

            }

        }
        return userService.save(userPojos);


    }

    /**
     * Gets the all the users ,
     * return 5 of System Announcement, for each page, for each request
     *
     * @param
     * @return List<UserPojo>
     * @author atalay
     */

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_ALL_USERS.CODE)")
    @GetMapping(value = {"/users"})
    public List<UserPojo> getUsers() throws DataNotFoundException {

        return userService.getAllByVisible(true);
    }


    /**
     * Get the public key and
     * returns the user according to the public key
     *
     * @param publicKey
     * @return UserPojo
     * @author atalay
     */
    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_USER.CODE)")
    @GetMapping(value = {"/user/{publicKey}"})
    public UserPojo getUser(@PathVariable String publicKey) throws DataNotFoundException {

        if (publicKey == null) {
            throw new DataNotFoundException("Public key not found.");
        }

        UserPojo pojo = userService.getByPublicKey(publicKey);
        return pojo;

    }

    private boolean isValidUserPojo(UserPojo userPojo) throws EmptyFieldException {
        if (userPojo != null) {
            if (userPojo.getEmail() == null || userPojo.getEmail().isEmpty()) {
                throw new EmptyFieldException("Email field cannot be empty");
            }

            if (userPojo.getUsername() == null || userPojo.getUsername().isEmpty()) {
                throw new EmptyFieldException("Username field cannot be empty");
            }
            if (userPojo.getPassword() == null || userPojo.getPassword().isEmpty()) {
                throw new EmptyFieldException("Password field cannot be empty");

            }
            if (userPojo.getName() == null || userPojo.getName().isEmpty()) {
                throw new EmptyFieldException("Name field cannot be empty");
            }

            if (userPojo.getSurname() == null || userPojo.getSurname().isEmpty()) {
                throw new EmptyFieldException("Surname field cannot be empty");
            }
            if (userPojo.getAuthority() == null || userPojo.getAuthority().getPublicKey() == null || userPojo.getAuthority().getPublicKey().isEmpty()) {
                throw new EmptyFieldException("Authority field cannot be empty");
            }
            return true;


        }

        throw new EmptyFieldException("User object cannot be empty");
    }


}