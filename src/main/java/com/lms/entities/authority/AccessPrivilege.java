package com.lms.entities.authority;


import com.lms.entities.BaseEntity;
import com.lms.entities.authority.Privilege;
import com.lms.entities.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "access_privileges")
public class AccessPrivilege extends BaseEntity{

    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_access_privileges",
            joinColumns = @JoinColumn(name="access_privilege_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
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
