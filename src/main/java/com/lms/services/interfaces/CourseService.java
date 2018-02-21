package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
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

    boolean registerUserToCourse(Course course, User user) throws ExecutionFailException;


    List<CoursePojo> getNotRegisteredCourses() throws DataNotFoundException;

    List<CoursePojo> getNotRegisteredCoursesBySearchParam(String param) throws DataNotFoundException;


    List<CoursePojo> getNotRegisteredCourses(String userPublicKey) throws DataNotFoundException;


}
