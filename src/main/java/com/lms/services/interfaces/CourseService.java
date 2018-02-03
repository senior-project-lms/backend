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
}



/*
---------------------------------------------------------------------------------------------------------------------------
function: ​getCourseForAdmin
parameters: ​publicKey: String
returns: ​List<CoursePojo>
---------------------------------------------------------------------------------------------------------------------------
function: ​save
parameters: ​CoursePojo
returns: ​boolean
---------------------------------------------------------------------------------------------------------------------------
function: ​save
parameters: ​List<CoursePojo>
returns: ​boolean
---------------------------------------------------------------------------------------------------------------------------
function: ​getCourseStatusCounts
parameters:
returns: ​HashMap<String, Integer>
------------------------------------------------------------------------------------------------------------------------*
* */