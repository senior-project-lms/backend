package com.lms.controllers;

import com.lms.customExceptions.*;
import com.lms.pojos.UserPojo;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api"})
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;


    @GetMapping(value = {"/users/active"})
    public List<UserPojo> getAllUsers() throws DataNotFoundException {
        return userService.getAllByVisible(true);
    }

    @GetMapping(value = {"users/deactivated"})
    public List<UserPojo> getAllDeactivatedUsers() throws DataNotFoundException {
        return userService.getAllByVisible(false);
    }


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
    @PostMapping(value = {"/admin/user"})
    public boolean saveUser(@RequestBody UserPojo userPojo) throws ExecutionFailException, EmptyFieldException, DataNotFoundException, ExistRecordException {


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
    @PostMapping(value = {"/admin/users"})
    public boolean saveUsers(@RequestBody List<UserPojo> userPojos) throws ExecutionFailException, EmptyFieldException, DataNotFoundException, ExistRecordException {

        for (UserPojo pojo : userPojos) {
            if (!isValidUserPojo(pojo)) {
                throw new ExecutionFailException("No such user is saved");

            }

        }
        return userService.save(userPojos);


    }

    private boolean isValidUserPojo(UserPojo userPojo) throws EmptyFieldException, ExistRecordException {
        if (userPojo != null) {
            throw new EmptyFieldException("Object cannot be null.");
        } else if (userPojo.getEmail() == null || userPojo.getEmail().isEmpty()) {
            throw new EmptyFieldException("Email field cannot be empty");
        } else if (userPojo.getUsername() == null || userPojo.getUsername().isEmpty()) {
            throw new EmptyFieldException("Username field cannot be empty");
        } else if (userPojo.getPassword() == null || userPojo.getPassword().isEmpty()) {
            throw new EmptyFieldException("Password field cannot be empty");

        } else if (userPojo.getName() == null || userPojo.getName().isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        } else if (userPojo.getSurname() == null || userPojo.getSurname().isEmpty()) {
            throw new EmptyFieldException("Surname field cannot be empty");
        } else if (userPojo.getAuthority() == null || userPojo.getAuthority().getPublicKey() == null || userPojo.getAuthority().getPublicKey().isEmpty()) {
            throw new EmptyFieldException("Authority field cannot be empty");
        } else if (userService.userAlreadyExist(userPojo.getEmail())) {
            throw new ExistRecordException(String.format("%s email is already exist", userPojo.getEmail()));
        }
        return true;


    }

    /**
     * Gets the all the users ,
     * return 5 of System Announcement, for each page, for each request
     *
     * @param
     * @return List<UserPojo>
     * @author atalay
     */
    @GetMapping(value = {"/admin/users"})
    public List<UserPojo> getUsers() throws DataNotFoundException {

        return userService.getAllByVisible(true);
    }


    /**
     * Get the public key and
     * returns the user according to the public key
     *
     * @param publickey
     * @return UserPojo
     * @author atalay
     */
    @GetMapping(value = {"/admin/user/{publickey}"})
    public UserPojo getUser(@PathVariable String publickey) throws DataNotFoundException {

        if (publickey == null) {
            throw new DataNotFoundException("Public key not found.");
        }

        UserPojo pojo = userService.getByPublicKey(publickey);
        return pojo;

    }

    @GetMapping("/admin/users/status")
    public Map<String, Integer> getUsersStatus() {
        return userService.getUserStatus();
    }

    @PutMapping("/admin/user/{publicKey}/visible")
    public boolean setVisible(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, EmptyFieldException {
        if (publicKey != null || !publicKey.isEmpty()) {
            return userService.updateVisibility(publicKey, true);

        }
        throw new EmptyFieldException("PublicKey is empty");
    }

    @PutMapping("/admin/user/{publicKey}/invisible")
    public boolean setInvisible(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, EmptyFieldException {
        if (publicKey != null || !publicKey.isEmpty()) {
            return userService.updateVisibility(publicKey, false);
        }
        throw new EmptyFieldException("PublicKey is empty");

    }


}