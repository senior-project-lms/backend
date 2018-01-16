package com.lms.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */

@Entity
@Table(name = "privalages")
public class Privilege extends BaseEntity{

    private String code;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<UserCoursePrivilege> userCoursePrivileges;

    @ManyToMany
    @JoinTable(
            name = "default_user_type_privileges",
            joinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "user_type_default_privilege_id", referencedColumnName = "id"))
    private List<UserTypeDefaultPrivilege> userTypeDefaultPrivileges;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserCoursePrivilege> getUserCoursePrivileges() {
        return userCoursePrivileges;
    }

    public void setUserCoursePrivileges(List<UserCoursePrivilege> userCoursePrivileges) {
        this.userCoursePrivileges = userCoursePrivileges;
    }
}
