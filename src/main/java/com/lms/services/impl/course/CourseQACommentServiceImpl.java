package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.QA;
import com.lms.entities.course.QaComment;
import com.lms.pojos.course.QACommentPojo;
import com.lms.repositories.QACommentRepository;
import com.lms.services.interfaces.course.CourseQAService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.course.CourseQACommentService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCourseQACommentServiceImpl implements CourseQACommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private QACommentRepository QACommentRepository;

    @Autowired
    private CourseQAService courseQAService;

    @Autowired
    private CourseService courseService;


    @Override
    public QACommentPojo entityToPojo(QaComment entity) {
        QACommentPojo pojo = new QACommentPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;
    }

    @Override
    public QaComment pojoToEntity(QACommentPojo pojo) {
        QaComment entity = new QaComment();
        entity.setContent(entity.getContent());
        entity.setQa(entity.getQa());
        return entity;
    }

    @Override
    public List<QACommentPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException {
        QA answer = courseQAService.findByCoursePublicKey(questionPublicKey);
        List<QaComment> qaComments = QACommentRepository.findAllByQuestion(answer);

        List<QACommentPojo> pojos = new ArrayList<>();
        for (QaComment entity : qaComments) {
            pojos.add(entityToPojo(entity));
        }
        return pojos;

    }
}


   /* @Override
    public List<QACommentPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException {
        QA answer = courseQAService.findByCoursePublicKey(questionPublicKey);
        List<QaComment> qaAnswers = qaAnswerRepository.findAllByQuestionAndUpdatedAtGreaterThan(answer);

        List<QACommentPojo> pojos = new ArrayList<>();
        for (QaComment entity : qaAnswers) {
            pojos.add(entityToPojo(entity));
        }
        return pojos;
    }*/
