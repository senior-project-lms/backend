package com.lms.controllers;

import com.lms.components.ExceptionConverter;
import com.lms.customExceptions.*;
import com.lms.pojos.UserPojo;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class UserController {

    @Autowired
    private ExceptionConverter exceptionConverter;

    @Autowired
    private UserService userService;


    @GetMapping(value = {"/me"})
    public UserPojo getMe() throws DataNotFoundException, ExecutionFailException, ExistRecordException {

        try {
            return userService.getMe();
        }
        catch (ServiceException ex){
            exceptionConverter.convert(ex);
        }

        throw new DataNotFoundException("No such user data is found");
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
    @PostMapping(value = {"/admin/user"})
    public boolean saveUser(@RequestBody UserPojo userPojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException, ExistRecordException {
        try {
            if (isValidUserPojo(userPojo)) {
                return userService.save(userPojo);
            }
        }
        catch (ServiceException ex){
            exceptionConverter.convert(ex);
        }

        throw new ExecutionFailException("No such user is saved");
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
    @PostMapping(value = {"/admin/users"})
    public boolean saveUsers(@RequestBody List<UserPojo> userPojos) throws EmptyFieldException, DataNotFoundException, ExecutionFailException, ExistRecordException {

        try {
            for (UserPojo pojo : userPojos) {
                if (!isValidUserPojo(pojo)) {
                    throw new ExecutionFailException("No such user is saved");

                }

            }
            return userService.save(userPojos);

        } catch (ServiceException e) {
            exceptionConverter.convert(e);

        }
        throw new ExecutionFailException("No such user is saved");
    }

    private boolean isValidUserPojo(UserPojo userPojo) throws EmptyFieldException{
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