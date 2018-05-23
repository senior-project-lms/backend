package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Assignment;
import com.lms.pojos.course.CourseAssignmentPojo;

import java.util.List;

public interface CourseAssignmentService {

    CourseAssignmentPojo entityToPojo(Assignment entity);

    Assignment pojoToEntity(CourseAssignmentPojo pojo);

    List<CourseAssignmentPojo> getAll(String publicKey) throws DataNotFoundException;

    List<CourseAssignmentPojo> getAllForStudents(String publicKey) throws DataNotFoundException;


    CourseAssignmentPojo get(String publicKey) throws DataNotFoundException;

    CourseAssignmentPojo getForAuthStudent(String publicKey) throws DataNotFoundException;

    Assignment findByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(String coursePublicKey, CourseAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException;

    boolean update(String publicKey, CourseAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException;

    int getAssignmentsCountsOfCourse(String publicKey) throws DataNotFoundException;

    int getPendingCountsOfAssignments(String publicKey) throws DataNotFoundException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

    boolean publish(String publicKey, boolean status) throws DataNotFoundException, ExecutionFailException;


}