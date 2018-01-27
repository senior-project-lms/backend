package com.lms.controllers;

import com.lms.customExceptions.EmptyFieldException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umit.kas on 21.11.2017.
 */
@RestController
public class Test {


    @GetMapping("/test")
    public String test() {
        return "OK";
    }

//
//    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicId, T(com.lms.enums.Privilege).METHOD_Y)")
//    @RequestMapping(value = "/api/course/{publicId}/test2", method = RequestMethod.POST)
//    String courseTest2(@PathVariable String publicId){
//        return "Secured method2";
//    }




}
