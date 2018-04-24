package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Assignment;
import com.lms.pojos.course.CourseAssignmentPojo;

import java.util.List;

public interface CourseAssignmentService {

    CourseAssignmentPojo entityToPojo(Assignment entity);

    Assignment pojoToEntity(CourseAssignmentPojo pojo);

    List<CourseAssignmentPojo> getAllAssignmentsOfCourse(String publicKey) throws DataNotFoundException;

    boolean setResourceAssignment(String publicKey,Assignment assignment) throws ExecutionFailException,DataNotFoundException;

    CourseAssignmentPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    Assignment findByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(String coursePublicKey, CourseAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

}
