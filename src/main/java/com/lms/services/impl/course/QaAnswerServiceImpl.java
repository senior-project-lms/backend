package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.QaAnswer;
import com.lms.entities.course.QaQuestion;
import com.lms.pojos.course.QaAnswerPojo;
import com.lms.repositories.QaAnswerRepository;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.QaAnswerService;
import com.lms.services.interfaces.QaQuestionService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QaAnswerServiceImpl implements QaAnswerService {

    @Autowired
    private UserService userService;

    @Autowired
    private QaAnswerRepository qaAnswerRepository;

    @Autowired
    private QaQuestionService qaQuestionService;

    @Autowired
    private CourseService courseService;


    @Override
    public QaAnswerPojo entityToPojo(QaAnswer entity) {
        QaAnswerPojo pojo = new QaAnswerPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;
    }

    @Override
    public QaAnswer pojoToEntity(QaAnswerPojo pojo) {
        QaAnswer entity = new QaAnswer();
        entity.setContent(entity.getContent());
        entity.setQuestion(entity.getQuestion());
        return entity;
    }

    @Override
    public List<QaAnswerPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException {
        QaQuestion answer = qaQuestionService.findByCoursePublicKey(questionPublicKey);
        List<QaAnswer> qaAnswers = qaAnswerRepository.findAllByQuestion(answer);

        List<QaAnswerPojo> pojos = new ArrayList<>();
        for (QaAnswer entity : qaAnswers) {
            pojos.add(entityToPojo(entity));
        }
        return pojos;

    }
}


   /* @Override
    public List<QaAnswerPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException {
        QaQuestion answer = qaQuestionService.findByCoursePublicKey(questionPublicKey);
        List<QaAnswer> qaAnswers = qaAnswerRepository.findAllByQuestionAndUpdatedAtGreaterThan(answer);

        List<QaAnswerPojo> pojos = new ArrayList<>();
        for (QaAnswer entity : qaAnswers) {
            pojos.add(entityToPojo(entity));
        }
        return pojos;
    }*/
