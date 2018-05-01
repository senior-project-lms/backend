package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.CourseQTQuestion;
import com.lms.entities.course.CourseQuizTest;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQTAnswerPojo;
import com.lms.pojos.course.CourseQTQuestionPojo;
import com.lms.repositories.CourseQTQuestionRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQTAnswerService;
import com.lms.services.interfaces.course.CourseQTQuestionService;
import com.lms.services.interfaces.course.CourseQTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQTQuestionServiceImpl implements CourseQTQuestionService {

    @Autowired
    private CourseQTQuestionRepository qtQuestionRepository;

    @Autowired
    private CourseQTService courseQTService;


    @Autowired
    private CourseQTAnswerService qtAnswerService;


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    public CourseQTQuestionPojo entityToPojo(CourseQTQuestion entity) {

        CourseQTQuestionPojo pojo = new CourseQTQuestionPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setOrder(entity.getOrder());

        if (entity.getAnswers() != null) {
            List<CourseQTAnswerPojo> pojos = entity.getAnswers()
                    .stream()
                    .map(e -> qtAnswerService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setAnswers(pojos);
        }

        return pojo;
    }

    @Override
    public CourseQTQuestion pojoToEntity(CourseQTQuestionPojo pojo) {
        CourseQTQuestion entity = new CourseQTQuestion();
        entity.setContent(pojo.getContent());
        entity.setOrder(pojo.getOrder());

        return entity;
    }

    @Override
    @Transactional
    public SuccessPojo save(String qtPublicKey, CourseQTQuestionPojo pojo) throws DataNotFoundException, ExecutionFailException {
        final User authUser = customUserDetailService.getAuthenticatedUser();
        final CourseQuizTest quizTest = courseQTService.findByPublicKey(qtPublicKey);
        CourseQTQuestion entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(authUser);
        entity.setQuizTest(quizTest);


        entity = qtQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a question is saved");
        }

        if (!qtAnswerService.save(entity.getPublicKey(), pojo.getAnswers())) {
            throw new ExecutionFailException("No such a answer set is saved");
        }


        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo delete(String publicKey) throws DataNotFoundException {
        final User authUser = customUserDetailService.getAuthenticatedUser();
        CourseQTQuestion entity = qtQuestionRepository.findByPublicKeyAndVisible(publicKey, true);

        if (entity == null) {
            throw new DataNotFoundException("No such a question is found");
        }

        entity.setUpdatedBy(authUser);
        entity.setVisible(false);

        entity = qtQuestionRepository.save(entity);

        if (entity == null) {
            throw new DataNotFoundException("No such a question is found");
        }


        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo update(String qtPublicKey, String publicKey, CourseQTQuestionPojo pojo) {
        return null;
    }

    @Override
    public CourseQTQuestionPojo get(String publicKey) throws DataNotFoundException {

        CourseQTQuestion entity = findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException("No such a question is found");
        }

        CourseQTQuestionPojo pojo = entityToPojo(entity);
        return pojo;
    }

    @Override
    public CourseQTQuestion findByPublicKey(String publicKey) throws DataNotFoundException {
        CourseQTQuestion entity = qtQuestionRepository.findByPublicKeyAndVisible(publicKey, true);

        if (entity != null && entity.getId() == 0) {
            throw new DataNotFoundException(String.format("No such a question is found by publicKey: %s", publicKey));
        }
        return entity;
    }
}
