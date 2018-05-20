package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.CourseResource;
import com.lms.entities.course.StudentAssignment;
import com.lms.pojos.course.CourseResourcePojo;
import org.modelmapper.internal.util.Lists;

import java.util.List;

public interface CourseResourceService{

    CourseResourcePojo entityToPojo(CourseResource entity);

    CourseResource pojoToEntity(CourseResourcePojo pojo);

    boolean saveEntities(List<CourseResource> resources);

    boolean save(String coursePublicKey,CourseResourcePojo pojo) throws DataNotFoundException,ExecutionFailException;

    boolean save(List<CourseResourcePojo> pojos) throws DataNotFoundException,ExecutionFailException;

    List<CourseResourcePojo> getCourseResources(String publicKey)throws DataNotFoundException;

    boolean setResourceAssignment(String publicKey,Assignment assignment) throws ExecutionFailException,DataNotFoundException;

    boolean setResourceStudentAssignment(String publicKey,StudentAssignment assignment) throws ExecutionFailException,DataNotFoundException;

    List<CourseResource> findByPublicKeys(List<String> publicKeys);

    CourseResourcePojo getByName(String name) throws DataNotFoundException;

    CourseResourcePojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean publiclyShared(String publicKey,boolean status) throws DataNotFoundException,ExecutionFailException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

}
