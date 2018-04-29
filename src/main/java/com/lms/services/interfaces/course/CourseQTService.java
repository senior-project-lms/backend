package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQuizTest;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQuizTestPojo;

import java.util.List;

public interface CourseQTService {

    CourseQuizTestPojo entityToPojo(CourseQuizTest entity);

    CourseQuizTest pojoToEntity(CourseQuizTestPojo pojo);

    SuccessPojo save(String coursePublicKey, CourseQuizTestPojo pojo) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo update(String publicKey, CourseQuizTestPojo pojo) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo publish(String publicKey) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo disable(String publicKey) throws DataNotFoundException, ExecutionFailException;


    List<CourseQuizTestPojo> getAll(String coursePublicKey) throws DataNotFoundException;

    CourseQuizTestPojo get(String publicKey) throws DataNotFoundException;

    CourseQuizTestPojo getForExam(String publicKey) throws DataNotFoundException;

    CourseQuizTestPojo getBeforeStartTheExam(String publicKey) throws DataNotFoundException;



    CourseQuizTest findByPublicKey(String publicKey) throws DataNotFoundException;

}
