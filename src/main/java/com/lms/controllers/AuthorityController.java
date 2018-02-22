package com.lms.controllers;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.pojos.AuthorityPojo;
import com.lms.services.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})


public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_ALL_AUTHORITIES.CODE)")
    @GetMapping("/access-levels")
    public List<AuthorityPojo> getAccessLevel() {
        return authorityService.getAuthorities();
    }


}

