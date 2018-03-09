package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPojo extends BasePojo {


    private String username;

    private String email;

    private String name;

    private String surname;

    private String password;

    private AuthorityPojo authority;

    private List<Long> accessPrivileges;

}

