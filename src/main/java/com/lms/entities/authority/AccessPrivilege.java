package com.lms.entities.authority;


import com.lms.entities.BaseEntity;
import com.lms.entities.authority.Privilege;
import com.lms.entities.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "access_privileges")
public class AccessPrivilege extends BaseEntity{

    @OneToOne
    private User user;

    @ManyToMany
    private List<Privilege> privileges;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}
