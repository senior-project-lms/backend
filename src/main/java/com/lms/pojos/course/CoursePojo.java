package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.user.UserPojo;

import java.util.List;

public class CoursePojo extends BasePojo {

    private String code;

    private String name;

    private List<UserPojo> registeredUsers;

    private List<UserCoursePrivilegePojo> userCoursePrivileges;

    private List<GradeTypePojo> gradeTypes;


    private List<AssignmentPojo> assignments;

}


/*
*



    @OneToMany(mappedBy = "course")
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "course")
    private List<QaQuestion> qaQuestions;

    @OneToMany(mappedBy = "course")
    private List<QuizTest> quizTests;

    @NotNull
    @ManyToOne
    private User owner;
*
* */