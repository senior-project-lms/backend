package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.UserCoursePrivilege;

import java.util.List;

public interface UserCoursePrivilegeService {


    List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible) throws DataNotFoundException;

    boolean existByUser(User user);


    boolean saveUserCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException;

    List<Long> getAllCoursePrivilegeCodes();

    List<Long> getLecturerDefaultPrivilegeCodes();

    List<Long> getAssistantDefaultPrivilegeCodes();

    List<Long> getStudentDefaultPrivilegeCodes();
}
