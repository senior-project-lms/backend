package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.StudentAssignment;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.StudentAssignmentPojo;

import java.util.List;

public interface StudentAssignmentService {

    StudentAssignmentPojo entityToPojo(StudentAssignment entity);

    StudentAssignment pojoToEntity(StudentAssignmentPojo pojo);

    List<StudentAssignmentPojo> getAllStudentAssignments(String assignmentPublicKey) throws DataNotFoundException;

    StudentAssignmentPojo getAuthStudentAssignment(String assignmentPublicKey) throws DataNotFoundException;

    StudentAssignmentPojo getByPublicKey(String publicKey) throws DataNotFoundException;


    SuccessPojo save(String assignmentPublicKey, StudentAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException;

    SuccessPojo update(String publicKey, StudentAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException;

}
