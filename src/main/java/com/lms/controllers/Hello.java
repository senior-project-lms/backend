package com.lms.controllers;

import com.lms.repositories.CourseRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umit.kas on 21.11.2017.
 */
@RestController
public class Hello {


    @Autowired
    CourseRepositoy courseRepositoy;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    String helloWorld(){
        return "Hello World";
    }

    @RequestMapping(value = "/api/hello", method = RequestMethod.POST)
    String securehelloWorld(){

        return SecurityContextHolder.getContext().getAuthentication().toString();
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicId, T(com.lms.properties.Privileges).METHOD_X)")
    @RequestMapping(value = "/api/course/{publicId}/test", method = RequestMethod.POST)
    String courseTest(@PathVariable String publicId){
        return "Secured method1";
    }



    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicId, T(com.lms.properties.Privileges).METHOD_Y)")
    @RequestMapping(value = "/api/course/{publicId}/test2", method = RequestMethod.POST)
    String courseTest2(@PathVariable String publicId){
        return "Secured method2";
    }

}
