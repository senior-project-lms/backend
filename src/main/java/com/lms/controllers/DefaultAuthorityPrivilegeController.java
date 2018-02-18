package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.pojos.DefaultAuthorityPrivilegePojo;
import com.lms.services.interfaces.DefaultAuthorityPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class DefaultAuthorityPrivilegeController {

    @Autowired
    private DefaultAuthorityPrivilegeService defaultAuthorityPrivilegeService;


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_DEFAULT_AUTHORITIES_AND_PRIVILEGES.CODE)")
    @GetMapping("/authority-privileges")
    public List<DefaultAuthorityPrivilegePojo> getDefaultAuthorityPrivileges() throws DataNotFoundException {

        return defaultAuthorityPrivilegeService.getDefaultAuthorityPrivileges();

    }

}
