package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.Grade;
import com.lms.entities.course.Score;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.ScorePojo;
import com.lms.pojos.course.UserScorePojo;
import com.lms.repositories.CourseScoreRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseScoreService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
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

    public SuccessPojo save(String gradePublicKey, UserScorePojo pojo) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailsService.getAuthenticatedUser();

        User user = userService.findByPublicKey(pojo.getUserPublicKey());

        Grade grade = courseGradeService.findByPublicKey(gradePublicKey);



        Score entity = null;
        if (courseScoreRepository.existsByGradeAndStudentAndVisible(grade, user, true)){
            entity = courseScoreRepository.findByGradeAndStudentAndVisible(grade, user, true);
            entity.setUpdatedBy(authUser);
        }
        else{
            entity = new Score();
            entity.generatePublicKey();

            entity.setStudent(user);
            entity.setGrade(grade);

            entity.setCreatedBy(authUser);
        }


        entity.setScore(pojo.getScore());




        entity = courseScoreRepository.save(entity);

        if (entity == null){
            throw new ExecutionFailException("No such a score is saved");
        }

        return new SuccessPojo(entity.getPublicKey());
    }



    @Override
    public List<ScorePojo> getCourseScoresOfAuthUser(String coursePublicKey) throws DataNotFoundException {

        User authUser = userDetailsService.getAuthenticatedUser();

        List<Grade> grades = courseGradeService.findAll(coursePublicKey);

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
        List<Grade> grades = courseGradeService.findAll(coursePublicKey);
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
