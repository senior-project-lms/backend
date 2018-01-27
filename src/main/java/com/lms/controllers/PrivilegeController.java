package com.lms.controllers;

import com.lms.services.interfaces.AccessPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class PrivilegeController {

    @Autowired
    private AccessPrivilegeService accessPrivilegeService;


    @GetMapping("/me/privileges")
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
