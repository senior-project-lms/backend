package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.QA;
import com.lms.pojos.course.QAPojo;
import com.lms.repositories.QARepository;
import com.lms.repositories.QAVoteRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQACommentService;
import com.lms.services.interfaces.course.CourseQAService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseCourseQAServiceImpl implements CourseQAService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private QARepository QARepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseQACommentService qaCommentService;

    @Autowired
    private QAVoteRepository QAVoteRepository;


    /**
     * Converts QAPojo to QA according to values, if the value is null passes it,
     *
     * @param pojo
     * @return QA
     * @author emsal aynaci
     */

    public QA pojoToEntity(QAPojo pojo) {

        QA entity = new QA();
        entity.setContent(pojo.getContent());
        entity.setTitle(pojo.getTitle());
        return entity;
    }

    /**
     * Converts QA entity to Question pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param entity
     * @return QAPojo
     * @author emsal aynaci
     */

    public QAPojo entityToPojo(QA entity) {

        QAPojo pojo = new QAPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;
    }


    /**
     *
     * Returns a list of 10 QaQuestionPojos,
     * Selects the visible QA and converts it to pojo than returns
     *
     * @author emsal aynaci
     * @param page
     * @return List<QAPojo>
     */

    @Override
    public List<QAPojo> getAllByPage(int page) throws DataNotFoundException {
        List<QAPojo> pojos = new ArrayList<>();

        List<QA> entities = QARepository.findAllByCourseAndVisible(true, new PageRequest(page,10));

        if (entities == null){
            throw new DataNotFoundException("no such a question is found");
        }

        QAPojo pojo;
        for (QA entity : entities) {
            pojo = entityToPojo(entity);
            pojos.add(pojo);

        }
        return pojos;
    }


    @Override
    public QAPojo getByPublicKey(String publicKey) throws DataNotFoundException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        QAPojo pojo;
        QA entity = QARepository.findByCoursePublicKey(publicKey);
        List<QA> answersEntities = QARepository.findAllByParentAndVisible(entity, true);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }
        pojo = entityToPojo(entity);

        pojo.setUpCount(QAVoteRepository.countByQaAndUpAndVisible(entity, true, true));
        pojo.setDownCount(QAVoteRepository.countByQaAndDownAndVisible(entity, true, true));

        pojo.setStarred(QAVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setUpped(QAVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setDowned(QAVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setStarCount(QAVoteRepository.countByQaAndStarAndVisible(entity, true, true));

        if (answersEntities != null){

            List<QAPojo> answerPojos = answersEntities
                    .stream()
                    .map(e -> {
                        QAPojo p =  entityToPojo(e);
                        p.setUpCount(QAVoteRepository.countByQaAndUpAndVisible(e, true, true));
                        p.setDownCount(QAVoteRepository.countByQaAndDownAndVisible(e, true, true));
                        p.setStarred(QAVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setUpped(QAVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setDowned(QAVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setStarCount(QAVoteRepository.countByQaAndStarAndVisible(e, true, true));

                        return p;
                    })
                    .collect(Collectors.toList());
            pojo.setAnswers(answerPojos);
        }

    }


    /**
     *
     * Return the QA,
     * findBy Course publicKey
     * return entity

     * @author emsal aynaci
     * @param publicKey
     * @return QA
     */
    @Override
    public QA findByCoursePublicKey(String publicKey) throws DataNotFoundException {
        QA entity = QARepository.findByCoursePublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        return entity;
    }

    @Override
    public boolean save(QAPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(pojo.getCourse().getPublicKey());
        QA entity = pojoToEntity(pojo);
        entity.generatePublicKey();


        if(entity.getCourse()!= null){
            entity.setCourse(course);
            entity.setCreatedBy(authenticatedUser);
        }

        entity = QARepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new ExecutionFailException("No such a question is saved");
        }

        return true;
    }


    /**
     *
     * Update the QA,
     * add who updates it,
     * update values
     * then save it.
     *
     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */
    @Override
    public boolean update(QAPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        String publicKey = pojo.getCourse().getPublicKey();
        QA entity = QARepository.findByCoursePublicKey(publicKey);
        if (entity == null){
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        entity.setUpdatedBy(authenticatedUser);
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity = QARepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new DataNotFoundException("No such a question is updated");
        }

        return true;

    }

    /**
     *
     * Delete the QA,
     * Delete means set visible false to property of it.
     * update visibility false and then save it.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return boolean
     */
    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        QA entity = QARepository.findByCoursePublicKey(publicKey);
        if (entity == null){
            throw new DataNotFoundException("No such a question is found publicKey");
        }
        entity.setVisible(false);
        entity = QARepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new DataNotFoundException("System resource is not deleted by publicKey");
        }
        return true;

    }


}