package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.QaAnswer;
import com.lms.entities.course.QaQuestion;
import com.lms.pojos.course.QaAnswerPojo;
import com.lms.pojos.course.QaQuestionPojo;
import com.lms.repositories.QaQuestionRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.QaAnswerService;
import com.lms.services.interfaces.QaQuestionService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QaQuestionServiceImpl implements QaQuestionService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private QaQuestionRepository qaQuestionRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private QaAnswerService qaAnswerService;


    /**
     * Converts QaQuestionPojo to QaQuestion according to values, if the value is null passes it,
     *
     * @param pojo
     * @return QaQuestion
     * @author emsal aynaci
     */

    public QaQuestion pojoToEntity(QaQuestionPojo pojo) {

        QaQuestion entity = new QaQuestion();
        entity.setContent(pojo.getContent());
        entity.setTitle(pojo.getTitle());
        return entity;
    }

    /**
     * Converts QaQuestion entity to Question pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param entity
     * @return QaQuestionPojo
     * @author emsal aynaci
     */

    public QaQuestionPojo entityToPojo(QaQuestion entity) {

        QaQuestionPojo pojo = new QaQuestionPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;
    }


    /**
     *
     * Returns a list of 10 QaQuestionPojos,
     * Selects the visible QaQuestion and converts it to pojo than returns
     *
     * @author emsal aynaci
     * @param page
     * @return List<QaQuestionPojo>
     */

    @Override
    public List<QaQuestionPojo> getAllByPage(int page) throws DataNotFoundException {
        List<QaQuestionPojo> pojos = new ArrayList<>();

        List<QaQuestion> entities = qaQuestionRepository.findAllByCourseAndVisible(true, new PageRequest(page,10));

        if (entities == null){
            throw new DataNotFoundException("no such a question is found");
        }

        QaQuestionPojo pojo;
        for (QaQuestion entity : entities) {
            pojo = entityToPojo(entity);
            pojos.add(pojo);

        }
        return pojos;
    }


    @Override
    public QaQuestionPojo getByPublicKey(String publicKey) throws DataNotFoundException {
        QaQuestionPojo pojo;
        QaQuestion entity = qaQuestionRepository.findByCoursePublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }
        pojo = entityToPojo(entity);

        /* List<QaAnswerPojo> answers = entity.getAnswers()
            .stream()
            .map(answerEntity -> QaAnswerService.entityToPojo(answerEntity))
            .collect(Collectors.toList());*/

        List<QaAnswerPojo> answers = new ArrayList<>();
        for(QaAnswer answer: entity.getAnswers())
        {
            answers.add(qaAnswerService.entityToPojo(answer));

        }
        pojo.setAnswers(answers);
        return pojo;
    }


    /**
     *
     * Return the QaQuestion,
     * findBy Course publicKey
     * return entity

     * @author emsal aynaci
     * @param publicKey
     * @return QaQuestion
     */
    @Override
    public QaQuestion findByCoursePublicKey(String publicKey) throws DataNotFoundException {
        QaQuestion entity = qaQuestionRepository.findByCoursePublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        return entity;
    }

    @Override
    public boolean save(QaQuestionPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(pojo.getCourse().getPublicKey());
        QaQuestion entity = pojoToEntity(pojo);
        entity.generatePublicKey();


        if(entity.getCourse()!= null){
            entity.setCourse(course);
            entity.setCreatedBy(authenticatedUser);
        }

        entity = qaQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new ExecutionFailException("No such a question is saved");
        }

        return true;
    }


    /**
     *
     * Update the QaQuestion,
     * add who updates it,
     * update values
     * then save it.
     *
     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */
    @Override
    public boolean update(QaQuestionPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        String publicKey = pojo.getCourse().getPublicKey();
        QaQuestion entity = qaQuestionRepository.findByCoursePublicKey(publicKey);
        if (entity == null){
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        entity.setUpdatedBy(authenticatedUser);
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity = qaQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new DataNotFoundException("No such a question is updated");
        }

        return true;

    }

    /**
     *
     * Delete the QaQuestion,
     * Delete means set visible false to property of it.
     * update visibility false and then save it.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return boolean
     */
    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        QaQuestion entity = qaQuestionRepository.findByCoursePublicKey(publicKey);
        if (entity == null){
            throw new DataNotFoundException("No such a question is found publicKey");
        }
        entity.setVisible(false);
        entity = qaQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new DataNotFoundException("System resource is not deleted by publicKey");
        }
        return true;

    }


}