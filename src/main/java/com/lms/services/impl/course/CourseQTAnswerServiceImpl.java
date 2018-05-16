package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.CourseQTAnswer;
import com.lms.entities.course.CourseQTQuestion;
import com.lms.entities.course.CourseQuizTest;
import com.lms.pojos.course.CourseQTAnswerPojo;
import com.lms.repositories.CourseQTAnswerRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQTAnswerService;

import com.lms.services.interfaces.course.CourseQTQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQTAnswerServiceImpl implements CourseQTAnswerService {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseQTQuestionService qtQuestionService;

    @Autowired
    private CourseQTAnswerService qtAnswerService;


    @Autowired
    private CourseQTAnswerRepository qtAnswerRepository;

    @Override
    public CourseQTAnswerPojo entityToPojo(CourseQTAnswer entity) {
        CourseQTAnswerPojo pojo = new CourseQTAnswerPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setText(entity.getText());
        pojo.setType(entity.getType());
        pojo.setCorrect(entity.isCorrect());

        return pojo;
    }

    @Override
    public CourseQTAnswer pojoToEntity(CourseQTAnswerPojo pojo) {
        CourseQTAnswer entity = new CourseQTAnswer();

        entity.setPublicKey(pojo.getPublicKey());
        entity.setType(pojo.getType());
        entity.setText(pojo.getText());
        entity.setCorrect(pojo.isCorrect());
        return entity;
    }


    @Override
    public boolean save(String questionPublicKey, List<CourseQTAnswerPojo> pojos) throws ExecutionFailException, DataNotFoundException {
        final User authUser = customUserDetailService.getAuthenticatedUser();
        final CourseQTQuestion question = qtQuestionService.findByPublicKey(questionPublicKey);

        List<CourseQTAnswer> entites = pojos
                .stream()
                .map(p -> {
                    CourseQTAnswer entity = pojoToEntity(p);
                    entity.setCreatedBy(authUser);
                    entity.generatePublicKey();
                    entity.setQuestion(question);
                    return entity;
                })
                .collect(Collectors.toList());


        entites = qtAnswerRepository.save(entites);

        if (entites == null || entites.size() == 0) {
            throw new ExecutionFailException("No such a answer collection is saved");
        }

        return true;
    }

    @Override
    public boolean update(String questionPublicKey, List<CourseQTAnswerPojo> pojos) {
        return false;
    }

    @Override
    public boolean delete(String questionPublicKey, String answerPublicKey) {
//
//        final User authUser = customUserDetailService.getAuthenticatedUser();
//        final CourseQTQuestion question = qtQuestionService.findByPublicKey(questionPublicKey);
//
//        CourseQTAnswer entity = qtAnswerRepository.findByPublicKeyAndQuestionAndVisible(answerPublicKey, question, true);
//
//        if (entity == null){
//        }
//
//        entity.setVisible(false);
//        entity.setUpdatedBy(authUser);
//        entity = qtAnswerRepository.save(entity);
//
//        if (entity == null) {
//        }

        return true;

    }


    @Override
    public CourseQTAnswer findByPublicKey(String publicKey) throws DataNotFoundException {

        CourseQTAnswer entity = qtAnswerRepository.findByPublicKeyAndVisible(publicKey, true);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a answer is found by publicKey: %s", publicKey));
        }

        return entity;
    }

    @Override
    public List<CourseQTAnswer> findByPublicKeyIn(List<String> publicKeys) throws DataNotFoundException {

        List<CourseQTAnswer> entities = qtAnswerRepository.findByPublicKeyInAndVisible(publicKeys, true);

        if (entities == null) {
            throw new DataNotFoundException(String.format("No such a answer collection is found"));
        }

        return entities;
    }



}
