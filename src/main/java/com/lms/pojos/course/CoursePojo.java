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

    private List<AnnouncementPojo> announcements;

    private List<QaQuestionPojo> qaQuestions;

    private List<QuizTestPojo> quizTests;


    private UserPojo owner;

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

    public List<UserPojo> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(List<UserPojo> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public List<UserCoursePrivilegePojo> getUserCoursePrivileges() {
        return userCoursePrivileges;
    }

    public void setUserCoursePrivileges(List<UserCoursePrivilegePojo> userCoursePrivileges) {
        this.userCoursePrivileges = userCoursePrivileges;
    }

    public List<GradeTypePojo> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<GradeTypePojo> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }

    public List<AssignmentPojo> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentPojo> assignments) {
        this.assignments = assignments;
    }

    public List<AnnouncementPojo> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<AnnouncementPojo> announcements) {
        this.announcements = announcements;
    }

    public List<QaQuestionPojo> getQaQuestions() {
        return qaQuestions;
    }

    public void setQaQuestions(List<QaQuestionPojo> qaQuestions) {
        this.qaQuestions = qaQuestions;
    }

    public List<QuizTestPojo> getQuizTests() {
        return quizTests;
    }

    public void setQuizTests(List<QuizTestPojo> quizTests) {
        this.quizTests = quizTests;
    }

    public UserPojo getOwner() {
        return owner;
    }

    public void setOwner(UserPojo owner) {
        this.owner = owner;
    }
}
