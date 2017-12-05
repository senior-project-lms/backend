package com.lms.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */

@Entity
@Table(name = "privalages")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String publicId;

    private String code;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<UserCoursePrivilege> userCoursePrivileges;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

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
