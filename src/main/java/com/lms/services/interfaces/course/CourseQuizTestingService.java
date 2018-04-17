package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.CourseQuizTest;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQuizTestPojo;

import java.util.List;

public interface CourseQuizTestingService {

    CourseQuizTestPojo entityToPojo(CourseQuizTest entity);

    CourseQuizTest pojoToEntity(CourseQuizTestPojo pojo);

    SuccessPojo save(String coursePublicKey, CourseQuizTestPojo pojo) throws DataNotFoundException;

    SuccessPojo update(String publicKey, CourseQuizTestPojo pojo) throws DataNotFoundException;

    SuccessPojo delete(String coursePublicKey, String publicKey);

    SuccessPojo publish(String coursePublicKey, String publicKey);

    List<CourseQuizTestPojo> getAll(String coursePublicKey) throws DataNotFoundException;

    CourseQuizTestPojo get(String publicKey);

    CourseQuizTest findByPublicKey(String publicKey, boolean published) throws DataNotFoundException;

}
