package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.CourseQTQuestion;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQTQuestionPojo;

public interface CourseQTQuestionService {

    CourseQTQuestionPojo entityToPojo(CourseQTQuestion entity);

    CourseQTQuestion pojoToEntity(CourseQTQuestionPojo pojo);

    SuccessPojo save(String qtPublicKey, CourseQTQuestionPojo pojo) throws DataNotFoundException;

    SuccessPojo delete(String publicKey);

    SuccessPojo update(String qtPublicKey, String publicKey, CourseQTQuestionPojo pojo);

    CourseQTQuestionPojo get(String publicKey);

    CourseQTQuestion findByPublicKey(String publicKey);
}


