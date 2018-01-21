package com.lms.entities.authority;

import com.lms.entities.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */

@Entity
@Table(name = "privileges")
public class Privilege extends BaseEntity {

    @Column(unique = true)
    private long code;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<UserCoursePrivilege> userCoursePrivileges;

    @ManyToMany
    @JoinTable(
            name = "default_user_type_privileges",
            joinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "user_type_default_privilege_id", referencedColumnName = "id"))
    private List<UserTypeDefaultPrivilege> userTypeDefaultPrivileges;


    @ManyToMany
    @JoinTable(
            name = "admin_user_privileges",
            joinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="admin_privilege_id", referencedColumnName = "id"))
    private List<AccessPrivilege> accessPrivileges;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<UserTypeDefaultPrivilege> getUserTypeDefaultPrivileges() {
        return userTypeDefaultPrivileges;
    }

    public void setUserTypeDefaultPrivileges(List<UserTypeDefaultPrivilege> userTypeDefaultPrivileges) {
        this.userTypeDefaultPrivileges = userTypeDefaultPrivileges;
    }

    public List<AccessPrivilege> getAccessPrivileges() {
        return accessPrivileges;
    }

    public void setAccessPrivileges(List<AccessPrivilege> accessPrivileges) {
        this.accessPrivileges = accessPrivileges;
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
