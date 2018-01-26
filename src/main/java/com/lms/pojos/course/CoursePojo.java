package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.UserPojo;
import lombok.Data;

import java.util.List;

@Data
public class CoursePojo extends BasePojo {

    private String code;

    private String name;

    private List<UserPojo> registeredUsers;

    private List<UserCoursePrivilegePojo> userCoursePrivileges;

    private List<GradeTypePojo> gradeTypes;


    private List<AssignmentPojo> assignments;

    private List<AnnouncementPojo> announcements;

    private List<QaQuestionPojo> qaQuestions;

    private List<QuizTestPojo> quizTests;


    private UserPojo owner;

}
