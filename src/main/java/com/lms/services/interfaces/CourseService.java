package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.course.Course;
import com.lms.pojos.course.CoursePojo;

import java.util.List;
import java.util.Map;

public interface CourseService {

    CoursePojo entityToPojo(Course entity);

    Course pojoToEntity(CoursePojo pojo);

    List<CoursePojo> getAllByVisible(boolean visible) throws ServiceException;

    CoursePojo getByPublicKey(String publicKey) throws ServiceException;

    boolean save(CoursePojo pojo) throws ServiceException;

    boolean save(List<CoursePojo> pojos) throws ServiceException;

    Map<String, Integer> getCourseStatus() throws ServiceException;

    boolean codeAlreadyExist(String code);

    boolean updateVisibility(String publicKey, boolean visible) throws ServiceException;

}
