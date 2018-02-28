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

    @NotNull
    @ManyToOne
    private User owner;


}
