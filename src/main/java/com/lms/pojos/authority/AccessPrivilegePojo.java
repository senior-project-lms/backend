package com.lms.pojos.authority;


import com.lms.pojos.BasePojo;
import com.lms.pojos.authority.PrivilegePojo;
import com.lms.pojos.user.UserPojo;

import java.util.List;

public class AccessPrivilegePojo extends BasePojo {

    private UserPojo user;

    private List<PrivilegePojo> privileges;


    public UserPojo getUser() {
        return user;
    }

    public void setUser(UserPojo user) {
        this.user = user;
    }

    public List<PrivilegePojo> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegePojo> privileges) {
        this.privileges = privileges;
    }
}
