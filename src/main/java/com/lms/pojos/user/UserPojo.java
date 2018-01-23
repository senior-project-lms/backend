package com.lms.pojos.user;

import com.lms.pojos.BasePojo;
import com.lms.pojos.authority.AuthorityPojo;

import java.util.List;

public class UserPojo extends BasePojo {


    private String username;

    private String email;

    private String name;

    private String surname;

    private String password;

    private AuthorityPojo authority;

    private List<Long> accessPrivileges;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthorityPojo getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityPojo authority) {
        this.authority = authority;
    }

    public List<Long> getAccessPrivileges() {
        return accessPrivileges;
    }

    public void setAccessPrivileges(List<Long> accessPrivileges) {
        this.accessPrivileges = accessPrivileges;
    }
}
