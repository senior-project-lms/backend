package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.pojos.PrivilegePojo;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_ALL_PRIVILEGES.CODE)")
    @GetMapping(value = {"/privileges"})
    public List<PrivilegePojo> getAllPrivileges() throws DataNotFoundException {
        return privilegeService.getAllPrivileges();
    }


}
