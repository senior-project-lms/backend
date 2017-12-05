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
public class Course extends BaseEntity{

    @Column(unique = true)
    private String code;

    private String name;

    @ManyToMany
    private List<User> registeredUsers;

    @OneToMany(mappedBy = "course")
    private List<UserCoursePrivilege> userCoursePrivileges;

    @OneToMany(mappedBy = "course")
    private List<GradeType> gradeTypes;

    @OneToMany(mappedBy = "course")
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "course")
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "course")
    private List<QaQuestion> qaQuestions;

    @OneToMany(mappedBy = "course")
    private List<QuizTest> quizTests;

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

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
}
