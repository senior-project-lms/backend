package com.lms.controllers;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.pojos.PrivilegePojo;
import com.lms.pojos.course.UserCoursePrivilegePojo;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class UserCoursePrivilegeController {

    @Autowired
    private UserCoursePrivilegeService userCoursePrivilegeService;



    @GetMapping(value = {"/course/{publicKey}/me/privileges"})
    public List<Long> getCoursePrivilegesOfAuthUser(@PathVariable String publicKey) throws DataNotFoundException {
        return userCoursePrivilegeService.getCoursePrivilegesOfAuthUser(publicKey);
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_AUTHENTICATED_USERS)")
    @GetMapping(value = {"/course/{publicKey}/assistants"})
    public List<UserCoursePrivilegePojo> getCourseAssistantUsers(@PathVariable String publicKey) throws DataNotFoundException {
        return userCoursePrivilegeService.getAssistantUsersOfCourse(publicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_AUTHENTICATED_USERS)")
    @GetMapping(value = {"/course/{publicKey}/privileges/all"})
    public List<PrivilegePojo> getAllCoursePrivileges(@PathVariable String publicKey) throws DataNotFoundException {
        return userCoursePrivilegeService.getAllCoursePrivileges();
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_AUTHENTICATED_USERS)")
    @GetMapping(value = {"/course/{publicKey}/privileges/assistant"})
    public List<PrivilegePojo> getAllCoursePrivilegesOfAssistant(@PathVariable String publicKey) throws DataNotFoundException {
        return userCoursePrivilegeService.getAllDefaultCoursePrivilegesOfAssistant();
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_AUTHENTICATED_USERS)")
    @GetMapping(value = {"/course/{publicKey}/assistant/{userPublicKey}"})
    public List<PrivilegePojo> getPrivilegesOfAssistantUsers(@PathVariable String publicKey, @PathVariable String userPublicKey) throws DataNotFoundException {
        return userCoursePrivilegeService.getAssistantPrivileges(publicKey, userPublicKey);
    }


}
