package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.pojos.UserPojo;
import com.lms.pojos.course.CoursePojo;

import java.util.List;
import java.util.Map;

public interface CourseService {

    CoursePojo entityToPojo(Course entity);

    Course pojoToEntity(CoursePojo pojo);

    List<CoursePojo> getAllByVisible(boolean visible) throws DataNotFoundException;

    CoursePojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(CoursePojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean save(List<CoursePojo> pojos) throws DataNotFoundException, ExecutionFailException;

    Map<String, Integer> getCourseStatus();

    boolean codeAlreadyExist(String code);

    boolean updateVisibility(String publicKey, boolean visible) throws ExecutionFailException, DataNotFoundException;

    Course findByPublicKey(String publicKey) throws DataNotFoundException;

    boolean registerUsersToCourse(Course course, List<User> users) throws ExecutionFailException;


    List<CoursePojo> getNotRegisteredCoursesByCodeByAuthUser(String param) throws DataNotFoundException;

    List<CoursePojo> getNotRegisteredCoursesByNameByAuthUser(String param) throws DataNotFoundException;

    List<CoursePojo> getNotRegisteredCoursesByLecturerByAuthUser(String name, String surname) throws DataNotFoundException;

    List<CoursePojo> getAuthUserCourses() throws DataNotFoundException;


    List<UserPojo> getEnrolledUsers(String publicKey) throws DataNotFoundException;


}
