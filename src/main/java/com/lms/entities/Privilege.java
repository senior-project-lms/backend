package com.lms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */

@Entity
@Table(name = "privileges")
@Data
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
            inverseJoinColumns = @JoinColumn(name = "user_type_default_privilege_id", referencedColumnName = "id"))
    private List<UserTypeDefaultPrivilege> userTypeDefaultPrivileges;


    @ManyToMany(mappedBy = "privileges")
    private List<AccessPrivilege> accessPrivileges;

}
