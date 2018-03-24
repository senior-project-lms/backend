package com.lms.services.interfaces.course;

import com.lms.entities.course.Score;
import com.lms.pojos.course.ScorePojo;

import java.util.List;

public interface CourseScoreService {

    ScorePojo entityToPojo(Score entity);

    Score pojoToEntity(ScorePojo pojo);


    boolean save(String coursePublicKey, String gradePublicKey, List<ScorePojo> pojos);

    boolean update(ScorePojo pojo);


}
