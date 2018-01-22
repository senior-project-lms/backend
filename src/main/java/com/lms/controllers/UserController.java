package com.lms.controllers;

import com.lms.pojos.user.UserPojo;
import com.lms.services.impl.user.UserServiceImpl;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api"})
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/me"}, method = RequestMethod.GET)
    public UserPojo getMe(){

        try {
            return userService.getMe();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
