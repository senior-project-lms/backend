package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */

@Entity
@Table(name = "courses")
@Data
public class Course extends BaseEntity {

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    private String name;

    @ManyToMany
    private List<User> registeredUsers;

    @ManyToMany
    private List<User> observerUsers;

    @ManyToMany
    private List<User> assistantUsers;


    @OneToMany(mappedBy = "course")
    private List<UserCoursePrivilege> userCoursePrivileges;

    @OneToMany(mappedBy = "course")
    private List<Grade> grades;

    @OneToMany(mappedBy = "course")
    private List<CourseResource> resources;

    @OneToMany(mappedBy = "course")
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "course")
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "course")
    private List<CourseQA> QAS;

    @OneToMany(mappedBy = "course")
    private List<CourseQuizTest> quizTests;

    @OneToMany(mappedBy = "course")
    private List<Event> events;

    @OneToMany(mappedBy = "course")
    private List<CourseQA> qas;

    @NotNull
    @ManyToOne
    private User owner;


}
