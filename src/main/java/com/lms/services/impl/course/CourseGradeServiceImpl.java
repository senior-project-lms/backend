package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.Grade;
import com.lms.entities.course.Score;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CoursePojo;
import com.lms.pojos.course.GradePojo;
import com.lms.pojos.course.ScorePojo;
import com.lms.repositories.CourseGradeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseScoreService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        pojo.setMaxScore(entity.getMaxScore());
        pojo.setPublished(entity.isPublished());

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
        //entity.setCourse(courseService.pojoToEntity(pojo.getCourse()));
        entity.setWeight(pojo.getWeight());
        entity.setMaxScore(pojo.getMaxScore());
        return entity;
    }

    @Override
    public List<GradePojo> getAll(String coursePublicKey) throws DataNotFoundException {

        List<Grade> entities = findAll(coursePublicKey);

        List<GradePojo> pojos = entities
                .stream()
                .map(entity -> {
                    GradePojo pojo = entityToPojo(entity);
                    pojo.setScores(null);

                    double average = entity
                            .getScores()
                            .stream()
                            .mapToDouble(Score::getScore)
                            .average()
                            .orElse(Double.NaN);
                    pojo.setAverage(average);
                    return pojo;
                })
                .collect(Collectors.toList());

        GradePojo gradePojo = new GradePojo();
        gradePojo.setName("Over all");
        gradePojo.setPublicKey("over-all");
        gradePojo.setMenu(false);
        gradePojo.setPublished(true);

        double overAllAverage = pojos
                .stream()
                .mapToDouble(GradePojo::getWeightedAverage)
                .sum();

        gradePojo.setOverAllAverage(overAllAverage);
        gradePojo.setMaxScore(100);

        pojos.add(gradePojo);


        return pojos;
    }



    @Override
    public GradePojo get(String publicKey) throws DataNotFoundException {

        Grade entity = findByPublicKey(publicKey);

        GradePojo pojo = entityToPojo(entity);

        double average = entity
                .getScores()
                .stream()
                .mapToDouble(Score::getScore)
                .average()
                .orElse(Double.NaN);
        pojo.setAverage(average);



        return pojo;
    }


    @Override
    public List<GradePojo> getAllForAuthStudent(String coursePublicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(coursePublicKey);

        List<Grade> entities = courseGradeRepository.findAllByCourseAndVisibleAndPublished(course, true, true);
        User authUser = userDetailService.getAuthenticatedUser();

        List<GradePojo> pojos = entities
                .stream()
                .map(entity -> {
                    GradePojo pojo = entityToPojo(entity);
                    pojo.setScores(null);

                    double average = entity
                            .getScores()
                            .stream()
                            .mapToDouble(Score::getScore)
                            .average()
                            .orElse(Double.NaN);
                    pojo.setAverage(average);

                    double score = entity.getScores()
                            .stream()
                            .filter(s -> s.getStudent().getPublicKey().equals(authUser.getPublicKey()))
                            .mapToDouble(Score::getScore).sum();
                    pojo.setScore(score);

                    return pojo;
                })
                .collect(Collectors.toList());

        GradePojo gradePojo = new GradePojo();
        gradePojo.setName("Over all");
        gradePojo.setPublicKey("over-all");
        gradePojo.setMenu(false);

        double overAll = pojos
                .stream()
                .mapToDouble(GradePojo::getWeightedScore)
                .sum();

        double overAllAverage = pojos
                .stream()
                .mapToDouble(GradePojo::getWeightedAverage)
                .sum();

        gradePojo.setOverAllAverage(overAllAverage);
        gradePojo.setOverAllGrade(overAll);
        gradePojo.setMaxScore(100);
        pojos.add(gradePojo);

        return pojos;
    }

    @Override
    public GradePojo getForView(String publicKey) throws DataNotFoundException {

        Grade entity = findByPublicKey(publicKey);

        GradePojo pojo = entityToPojo(entity);
        pojo.setScores(null);

        return pojo;
    }

    @Override
    public SuccessPojo save(String coursePublicKey, GradePojo pojo) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();

        Course course = courseService.findByPublicKey(coursePublicKey);

        Grade entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(authUser);
        entity.setCourse(course);


        entity = courseGradeRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("No such a grade is saved");
        }

        return new SuccessPojo(entity.getPublicKey());
    }


    @Override
    public SuccessPojo update(String publicKey, GradePojo pojo) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();

        Grade entity = findByPublicKey(publicKey);

        entity.setName(pojo.getName());
        entity.setMaxScore(pojo.getMaxScore());
        entity.setWeight(pojo.getWeight());

        entity.setUpdatedBy(authUser);

        entity = courseGradeRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("No such a grade is updated");
        }

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo delete(String publicKey) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();

        Grade entity = findByPublicKey(publicKey);

        entity.setUpdatedBy(authUser);
        entity.setVisible(false);

        entity = courseGradeRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("No such a grade is deleted");
        }

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public Grade findByPublicKey(String publicKey) throws DataNotFoundException {
        Grade entity = courseGradeRepository.findByPublicKeyAndVisible(publicKey, true);
        if (entity == null){
            throw new DataNotFoundException(String.format("No Such a grade is found by publicKey: %s", publicKey));
        }

        return entity;
    }


    @Override
    public List<Grade> findAll(String coursePublicKey) throws DataNotFoundException {

        Course course = courseService.findByPublicKey(coursePublicKey);

        List<Grade> entities = courseGradeRepository.findAllByCourseAndVisible(course, true);

        if (entities == null){
            new ArrayList<>();
        }

        return entities;
    }

    @Override
    public boolean publish(String publicKey, boolean status) throws DataNotFoundException, ExecutionFailException {
        Grade entity = findByPublicKey(publicKey);

        entity.setPublished(status);

        entity = courseGradeRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("No such a grade is deleted");
        }

        return true;
    }
}
