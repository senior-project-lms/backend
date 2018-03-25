package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.Score;
import com.lms.pojos.course.ScorePojo;

import java.util.List;

public interface CourseScoreService {

    ScorePojo entityToPojo(Score entity);

    Score pojoToEntity(ScorePojo pojo);


    boolean save(String coursePublicKey, String gradePublicKey, List<ScorePojo> pojos) throws DataNotFoundException;

    boolean updateScore(String scorePublicKey, ScorePojo pojo) throws DataNotFoundException;


    List<ScorePojo> getCourseScoresOfAuthUser(String coursePublicKey) throws DataNotFoundException;

    List<ScorePojo> getCourseScoresOfStudent(String coursePublicKey, String userPublicKey) throws DataNotFoundException;

}
