package com.lms.entities.authority;

import com.lms.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(schema = "user_type_default_settings")
public class UserTypeDefaultPrivilege extends BaseEntity {

    @OneToOne
    private UserType userType;

    @ManyToMany
    private List<Privilege> privileges;


    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}
