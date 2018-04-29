package com.lms.services.interfaces.course;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQTUser;
import com.lms.pojos.course.CourseQTUserPojo;
import com.lms.pojos.SuccessPojo;

public interface CourseQTUserService {


    CourseQTUserPojo entityToPojo(CourseQTUser entity);

    CourseQTUser pojoToEntity(CourseQTUserPojo pojo);

    SuccessPojo start(String coursePublicKey, String qtPublicKey) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo finish(String coursePublicKey, String qtPublicKey) throws DataNotFoundException, ExecutionFailException;

    CourseQTUserPojo get(String userPublicKey, String qtPublicKey);

    CourseQTUserPojo get(String qtPublicKey) throws DataNotFoundException;


    CourseQTUser findByPublicKey(String publicKey) throws DataNotFoundException;

    CourseQTUser findAuthUserQT(String qtPublicKey) throws DataNotFoundException;

    boolean available(String qtPublicKey);

    boolean isTimeUp(String qtPublicKey) throws DataNotFoundException;


}
