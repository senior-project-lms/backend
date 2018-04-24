package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseResource;
import com.lms.pojos.course.CourseResourcePojo;

import java.util.List;

public interface CourseResourceService{

    CourseResourcePojo entityToPojo(CourseResource entity);

    CourseResource pojoToEntity(CourseResourcePojo pojo);

    boolean saveEntities(List<CourseResource> resources);

    boolean save(String coursePublicKey,CourseResourcePojo pojo) throws DataNotFoundException,ExecutionFailException;

    boolean save(List<CourseResourcePojo> pojos);

    List<CourseResourcePojo> getCourseResources(String publicKey)throws DataNotFoundException;

    CourseResourcePojo getByName(String name) throws DataNotFoundException;

    CourseResourcePojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

}
