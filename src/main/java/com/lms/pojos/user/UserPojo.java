package com.lms.pojos.user;

import com.lms.pojos.BasePojo;
import com.lms.pojos.authority.AuthorityPojo;
import lombok.Data;

import java.util.List;

@Data
public class UserPojo extends BasePojo {


    private String username;

    private String email;

    private String name;

    private String surname;

    private String password;

    private AuthorityPojo authority;

    private List<Long> accessPrivileges;

}

