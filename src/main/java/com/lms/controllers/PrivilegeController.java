package com.lms.controllers;

import com.lms.services.impl.authority.AccessPrivilegeServiceImpl;
import com.lms.services.interfaces.AccessPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class PrivilegeController {

    @Autowired
    private AccessPrivilegeService accessPrivilegeService;

    @RequestMapping(value = {"/me/priveleges"}, method = RequestMethod.GET)
    public List<Long> getAdminPrivileges(){
        try {
            return accessPrivilegeService.getAuthenticatedUserAccessPrivileges();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


}
