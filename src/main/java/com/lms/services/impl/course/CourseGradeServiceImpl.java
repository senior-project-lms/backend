package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.Grade;
import com.lms.entities.course.Score;
import com.lms.pojos.course.CoursePojo;
import com.lms.pojos.course.GradePojo;
import com.lms.pojos.course.ScorePojo;
import com.lms.repositories.CourseGradeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseScoreService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseGradeServiceImpl implements CourseGradeService {

    @Autowired
    private CourseScoreService courseScoreService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseGradeRepository courseGradeRepository;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    public GradePojo entityToPojo(Grade entity) {

        GradePojo pojo = new GradePojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setWeight(entity.getWeight());

        if (entity.getScores() != null) {
            List<ScorePojo> scores = entity.getScores()
                    .stream()
                    .map(scoreEntity -> courseScoreService.entityToPojo(scoreEntity))
                    .collect(Collectors.toList());
            pojo.setScores(scores);
        }

        return pojo;
    }

    @Override
    public Grade pojoToEntity(GradePojo pojo) {
        Grade entity = new Grade();

        entity.setPublicKey(pojo.getPublicKey());
        entity.setName(pojo.getName());
        entity.setCourse(courseService.pojoToEntity(pojo.getCourse()));
        entity.setWeight(pojo.getWeight());

        return entity;
    }

    @Override
    public List<GradePojo> getAllGradesOfCourse(String coursePublicKey) throws DataNotFoundException {

        final Course course = courseService.findByPublicKey(coursePublicKey);
        List<Grade> entities = courseGradeRepository.findAllByCourseAndVisible(course, true);
        if (entities == null) {
            return new ArrayList<>();
        }
        List<GradePojo> pojos = entities
                .stream()
                .map(entity -> {
                    GradePojo pojo = entityToPojo(entity);
                    pojo.setScores(null);
                    return pojo;
                })
                .collect(Collectors.toList());
        return pojos;
    }

    @Override
    public GradePojo getGradeOfCourse(String coursePublicKey, String gradePublicKey) throws DataNotFoundException {

        Grade entity = findByPublicKeyAndCoursePublicKey(gradePublicKey, coursePublicKey);

        GradePojo pojo = entityToPojo(entity);

        return pojo;
    }

    @Override
    public boolean save(GradePojo pojo) {

        User authUser = userDetailService.getAuthenticatedUser();

        Grade entity = pojoToEntity(pojo);

        return false;
    }

    @Override
    public boolean updateWeight(GradePojo pojo) {
        return false;
    }

    @Override
    public boolean updateName(GradePojo pojo) {
        return false;
    }

    @Override
    public boolean delete(String coursePublicKey, String gradePublicKey) {
        return false;
    }

    @Override
    public Grade findByPublicKeyAndCoursePublicKey(String publicKey, String coursePublicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(coursePublicKey);
        Grade entity = courseGradeRepository.findByPublicKeyAndCourseAndVisible(publicKey, course, true);
        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a grade is found by publicKey: %s", publicKey));
        }

        return entity;
    }
}
