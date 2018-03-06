package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.UserCoursePrivilege;
import com.lms.enums.AccessLevel;
import com.lms.enums.ECoursePrivilege;
import com.lms.pojos.PrivilegePojo;
import com.lms.pojos.course.UserCoursePrivilegePojo;

import java.util.HashMap;
import java.util.List;

public interface UserCoursePrivilegeService {

    UserCoursePrivilegePojo entityToPojo(UserCoursePrivilege entity);

    UserCoursePrivilege pojoToEntity(UserCoursePrivilegePojo pojo);

    List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible) throws DataNotFoundException;

    boolean existByUser(User user);


    boolean saveStudentCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException;

    boolean saveCourseLecturerPrivileges(List<Course> courses) throws DataNotFoundException, ExecutionFailException;

    boolean saveAssistantCoursePrivilege(User user, Course course) throws DataNotFoundException, ExecutionFailException;

    boolean saveObserverUserCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException;


    List<Long> getCoursePrivilegesOfAuthUser(String coursePublicKey) throws DataNotFoundException;

    List<Long> getAllCoursePrivilegeCodes();

    List<Long> getLecturerDefaultPrivilegeCodes();

    List<Long> getAssistantDefaultPrivilegeCodes();

    List<Long> getObserverDefaultPrivilegeCodes();

    List<Long> getStudentDefaultPrivilegeCodes();


    boolean hasPrivilege(String coursePublicKey, ECoursePrivilege coursePrivilege) throws DataNotFoundException;

    List<UserCoursePrivilegePojo> getAssistantUsersOfCourse(String publicKey) throws DataNotFoundException;

    boolean saveUserCoursePrivilege(Course course, UserCoursePrivilegePojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean updateUserCoursePrivilege(String coursePublicKey, UserCoursePrivilegePojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean deleteUserCoursePrivilege(Course course, User user) throws DataNotFoundException, ExecutionFailException;

    List<PrivilegePojo> getAllCoursePrivileges() throws DataNotFoundException;

    List<PrivilegePojo> getAllDefaultCoursePrivilegesOfAssistant() throws DataNotFoundException;

    List<PrivilegePojo> getAssistantPrivileges(String coursePublicKey, String userPublicKey) throws DataNotFoundException;
}
