package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Score;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.ScorePojo;
import com.lms.pojos.course.UserScorePojo;

import java.util.List;

public interface CourseScoreService {

    ScorePojo entityToPojo(Score entity);

    Score pojoToEntity(ScorePojo pojo);



    SuccessPojo save(String gradePublicKey, UserScorePojo pojo) throws DataNotFoundException, ExecutionFailException;

    List<ScorePojo> getCourseScoresOfAuthUser(String coursePublicKey) throws DataNotFoundException;

    List<ScorePojo> getCourseScoresOfStudent(String coursePublicKey, String userPublicKey) throws DataNotFoundException;

}
