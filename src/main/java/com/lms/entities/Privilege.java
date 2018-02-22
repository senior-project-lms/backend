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


    @ManyToMany(mappedBy = "accessPrivileges")
    private List<User> users;

    @ManyToMany(mappedBy = "privileges")
    private List<DefaultAuthorityPrivilege> defaultAuthorityPrivileges;


}
