package com.lms.controllers;

import com.lms.pojos.user.UserPojo;
import com.lms.services.impl.user.UserServiceImpl;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/me"}, method = RequestMethod.GET)
    public UserPojo getMe() {

        try {
            return userService.getMe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    @RequestMapping(value = {"/admin/user"}, method = RequestMethod.POST)
    public boolean saveUser(@RequestBody UserPojo userPojo) {
        try {
            if (isValidUserPojo(userPojo)) {
                return userService.save(userPojo);
            }


        } catch (Exception e) {
            e.printStackTrace();
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
    @RequestMapping(value = {"/admin/users"}, method = RequestMethod.POST)
    public boolean saveUsers(@RequestBody List<UserPojo> userPojos) {

        try {
            if (userPojos == null || userPojos.size() == 0) {
                return false;
            }
            for (UserPojo pojo : userPojos) {
                if (!isValidUserPojo(pojo)) {
                    return false;
                }

            }
            return userService.save(userPojos);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    private boolean isValidUserPojo(UserPojo userPojo) {
        if (userPojo != null) {
            if (userPojo.getAuthority() == null || userPojo.getAuthority().getAccessLevel() == 0) {
                return false;
            }
            if (userPojo.getEmail() == null || userPojo.getEmail().isEmpty()) {
                return false;
            }
            if (userPojo.getName() == null || userPojo.getName().isEmpty()) {
                return false;
            }
            if (userPojo.getPassword() == null || userPojo.getPassword().isEmpty()) {
                return false;
            }
            if (userPojo.getUsername() == null || userPojo.getUsername().isEmpty()) {
                return false;
            }
            if (userPojo.getUsername() == null || userPojo.getSurname().isEmpty()) {
                return false;
            }
            return true;


        }
        return false;
    }
}