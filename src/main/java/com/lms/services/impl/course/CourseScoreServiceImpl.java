package com.lms.services.impl.course;

import com.lms.entities.course.Score;
import com.lms.pojos.course.ScorePojo;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseScoreService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CourseScoreServiceImpl implements CourseScoreService {


    @Autowired
    private CourseGradeService courseGradeService;

    @Autowired
    private UserService userService;

    @Override
    public ScorePojo entityToPojo(Score entity) {
        ScorePojo pojo = new ScorePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setScore(entity.getScore());
        pojo.setStudent(userService.entityToPojo(entity.getStudent()));

        return pojo;
    }

    @Override
    public Score pojoToEntity(ScorePojo pojo) {
        Score entity = new Score();

        entity.setPublicKey(pojo.getPublicKey());
        entity.setScore(pojo.getScore());
        entity.setStudent(userService.pojoToEntity(pojo.getStudent()));

        return entity;
    }

    @Override
    public boolean save(String coursePublicKey, String gradePublicKey, List<ScorePojo> pojos) {
        return false;
    }

    @Override
    public boolean update(ScorePojo pojo) {
        return false;
    }
}
