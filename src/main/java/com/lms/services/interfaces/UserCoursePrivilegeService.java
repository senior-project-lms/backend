package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.UserCoursePrivilege;
import com.lms.enums.AccessLevel;
import com.lms.enums.ECoursePrivilege;

import java.util.HashMap;
import java.util.List;

public interface UserCoursePrivilegeService {


    List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible) throws DataNotFoundException;

    boolean existByUser(User user);


    boolean saveStudentCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException;

    boolean saveCourseLecturerPrivileges(List<Course> courses) throws DataNotFoundException, ExecutionFailException;


    List<Long> getCoursePrivilegesOfAuthUser(String coursePublicKey) throws DataNotFoundException;

    List<Long> getAllCoursePrivilegeCodes();

    List<Long> getLecturerDefaultPrivilegeCodes();

    List<Long> getAssistantDefaultPrivilegeCodes();

    List<Long> getObserverDefaultPrivilegeCodes();

    List<Long> getStudentDefaultPrivilegeCodes();

    boolean hasPrivilege(String coursePublicKey, ECoursePrivilege coursePrivilege) throws DataNotFoundException;

}
