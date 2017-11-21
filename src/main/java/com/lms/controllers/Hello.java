package com.lms.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umit.kason 21.11.2017.
 */
@RestController
public class Hello {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String helloWorld(){
        return "Hello World";
    }
    @RequestMapping(value = "/api/hello", method = RequestMethod.GET)
    String securehelloWorld(){
        return "secure Hello World";
    }


}
