package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.Grade;
import com.lms.entities.course.Score;
import com.lms.pojos.course.ScorePojo;
import com.lms.repositories.CourseScoreRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseScoreService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class CourseScoreServiceImpl implements CourseScoreService {


    @Autowired
    private CourseGradeService courseGradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseScoreRepository courseScoreRepository;


    @Override
    public ScorePojo entityToPojo(Score entity) {
        ScorePojo pojo = new ScorePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setScore(entity.getScore());
        pojo.setStudent(userService.entityToPojo(entity.getStudent()));
        pojo.setGrade(courseGradeService.entityToPojo(entity.getGrade()));

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
    public boolean save(String coursePublicKey, String gradePublicKey, List<ScorePojo> pojos) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailsService.getAuthenticatedUser();

        Grade grade = courseGradeService.findByPublicKeyAndCoursePublicKey(gradePublicKey, coursePublicKey);

        Map<String, User> userMap =  new HashMap<>();

        List<String> studentPublicKeys = pojos
                .stream()
                .map(pojo -> pojo.getStudent().getPublicKey())
                .collect(Collectors.toList());


        List<User> users = userService.findAllByPublicKeyIn(studentPublicKeys);

        users
                .stream()
                .map(user -> userMap.put(user.getPublicKey(), user));


        List<Score> scores = pojos
                .stream()
                .map(pojo -> {
                    Score score = pojoToEntity(pojo);
                    score.setGrade(grade);
                    score.setStudent(userMap.get(pojo.getStudent().getPublicKey()));
                    score.setCreatedBy(authUser);
                    return score;
                })
                .collect(Collectors.toList());


        scores = courseScoreRepository.save(scores);

        if (scores == null || (scores != null && scores.size() == 0)){
            throw new ExecutionFailException("No such a grade collection is saved");
        }


        return true;
    }

    @Override
    public boolean updateScore(String scorePublicKey, ScorePojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailsService.getAuthenticatedUser();

        User student = userService.findByPublicKey(pojo.getStudent().getPublicKey());

        Score entity = courseScoreRepository.findByPublicKeyAndStudentAndVisible(scorePublicKey, student, true );

        if (entity == null){
            throw new DataNotFoundException(String.format("No such a score is found by publicKey: %s", scorePublicKey));
        }

        entity.setScore(pojo.getScore());

        entity.setUpdatedBy(authUser);

        entity = courseScoreRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("No such a grade is updated");

        }

        return true;
    }


    @Override
    public List<ScorePojo> getCourseScoresOfAuthUser(String coursePublicKey) throws DataNotFoundException {

        User authUser = userDetailsService.getAuthenticatedUser();

        List<Grade> grades = courseGradeService.findAllGradesOfCourse(coursePublicKey);

        List<Score> entities = courseScoreRepository.findAllByGradeInAndStudentAndVisible(grades, authUser, true);

        if (entities == null){
            new ArrayList<>();
        }


        List<ScorePojo> scores = entities
                .stream()
                .map(entity -> {
                    ScorePojo pojo = entityToPojo(entity);
                    pojo.setStudent(null);
                    return pojo;
                })
                .collect(Collectors.toList());

        return scores;
    }



    @Override
    public List<ScorePojo> getCourseScoresOfStudent(String coursePublicKey, String userPublicKey) throws DataNotFoundException {

        User user = userService.findByPublicKey(userPublicKey);
        List<Grade> grades = courseGradeService.findAllGradesOfCourse(coursePublicKey);
        List<Score> entities = courseScoreRepository.findAllByGradeInAndStudentAndVisible(grades, user, true);

        if (entities == null){
            new ArrayList<>();
        }


        List<ScorePojo> scores = entities
                .stream()
                .map(entity -> {
                    ScorePojo pojo = entityToPojo(entity);
                    pojo.setStudent(null);
                    return pojo;
                })
                .collect(Collectors.toList());

        return scores;
    }
}
