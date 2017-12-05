package com.lms.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String publicId;

    @Column(unique = true)
    private String code;

    private String name;

    @CreationTimestamp
    private Date date;

    @OneToOne
    private User createdBy;

    @ManyToMany
    private List<User> registeredUsers;

    @OneToMany(mappedBy = "course")
    private List<UserCoursePrivilege> userCoursePrivileges;

    @OneToMany(mappedBy = "course")
    private List<GradeType> gradeTypes;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(List<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public List<UserCoursePrivilege> getUserCoursePrivileges() {
        return userCoursePrivileges;
    }

    public void setUserCoursePrivileges(List<UserCoursePrivilege> userCoursePrivileges) {
        this.userCoursePrivileges = userCoursePrivileges;
    }

    public List<GradeType> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<GradeType> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }
}
