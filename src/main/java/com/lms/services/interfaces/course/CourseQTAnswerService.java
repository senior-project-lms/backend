package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQTAnswer;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQTAnswerPojo;

import java.util.List;

public interface CourseQTAnswerService {

    CourseQTAnswer pojoToEntity(CourseQTAnswerPojo pojo);

    CourseQTAnswerPojo entityToPojo(CourseQTAnswer entity);

    boolean save(String questionPublicKey, List<CourseQTAnswerPojo> pojos) throws ExecutionFailException, DataNotFoundException;

    boolean update(String questionPublicKey, List<CourseQTAnswerPojo> pojos);

    boolean delete(String questionPublicKey, String answerPublicKey);


    CourseQTAnswer findByPublicKey(String publicKey) throws DataNotFoundException;


    List<CourseQTAnswer> findByPublicKeyIn(List<String> publicKeys) throws DataNotFoundException;


}
