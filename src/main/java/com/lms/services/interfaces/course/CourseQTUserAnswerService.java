package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQTUserAnswer;
import com.lms.pojos.course.CourseQTUserAnswerPojo;
import com.lms.pojos.SuccessPojo;

import java.util.List;


public interface CourseQTUserAnswerService {


    CourseQTUserAnswerPojo entityToPojo(CourseQTUserAnswer entity);

    SuccessPojo setAnswer(String qtPublicKey, CourseQTUserAnswerPojo pojo) throws DataNotFoundException, ExecutionFailException;

    List<CourseQTUserAnswerPojo> getAnswers(String qtPublicKey, String userPublicKey) throws DataNotFoundException;


}
