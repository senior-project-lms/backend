package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.entities.User;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by umit.kas on 21.11.2017.
 */
@RestController
public class Test {


    @Autowired
    private UserService userService;

    @GetMapping(value = {"/usernames"})
    public List<String> getUsernames() throws DataNotFoundException {

        return userService.getAllUsernames();
    }



}
