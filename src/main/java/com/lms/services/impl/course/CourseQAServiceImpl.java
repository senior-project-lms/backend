package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.QA;
import com.lms.entities.course.QAComment;
import com.lms.entities.course.QAVote;
import com.lms.enums.VoteType;
import com.lms.pojos.course.QACommentPojo;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQAServiceImpl implements CourseQAService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private QARepository qaRepository;

    @Autowired
    private CourseService courseService;


    @Autowired
    private QAVoteRepository qaVoteRepository;


    @Autowired
    private CourseQACommentService courseQACommentService;

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

        if (!entity.isAnonymous()) {
            pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        }
        if (entity.getComments() != null) {
            List<QACommentPojo> comments = entity.getComments()
                    .stream()
                    .map(e -> courseQACommentService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setComments(comments);

        }
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
    public List<QAPojo> getAll(String coursePublicKey, int page) throws DataNotFoundException {

        Course course = courseService.findByPublicKey(coursePublicKey);

        List<QA> entities = qaRepository.findAllByCourseAndAnswerAndVisible(course, false, true, new PageRequest(page, 10));

        if (entities == null){
            throw new DataNotFoundException("no such a question is found");
        }

        List<QAPojo> pojos = entities
                .stream()
                .map(e -> {
                    QAPojo pojo = entityToPojo(e);
                    pojo.setComments(null);
                    pojo.setAnswers(null);
                    pojo.setUpCount(qaVoteRepository.countByQaAndUpAndVisible(e, true, true));
                    pojo.setDownCount(qaVoteRepository.countByQaAndDownAndVisible(e, true, true));
                    pojo.setAnswerCount(qaRepository.countByParentAndVisible(e, true));
                    pojo.setStarCount(qaVoteRepository.countByQaAndStarAndVisible(e, true, true));
                    return pojo;
                })
                .collect(Collectors.toList());
        return pojos;
    }


    @Override
    public QAPojo getByPublicKey(String publicKey) throws DataNotFoundException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        QAPojo pojo;
        QA entity = findByPublicKey(publicKey, true);

        List<QA> answersEntities = qaRepository.findAllByParentAndVisible(entity, true);

        pojo = entityToPojo(entity);

        pojo.setUpCount(qaVoteRepository.countByQaAndUpAndVisible(entity, true, true));
        pojo.setDownCount(qaVoteRepository.countByQaAndDownAndVisible(entity, true, true));

        pojo.setStarred(qaVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setUpped(qaVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setDowned(qaVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setStarCount(qaVoteRepository.countByQaAndStarAndVisible(entity, true, true));

        if (answersEntities != null){

            List<QAPojo> answerPojos = answersEntities
                    .stream()
                    .map(e -> {
                        QAPojo p =  entityToPojo(e);
                        p.setUpCount(qaVoteRepository.countByQaAndUpAndVisible(e, true, true));
                        p.setDownCount(qaVoteRepository.countByQaAndDownAndVisible(e, true, true));
                        p.setStarred(qaVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setUpped(qaVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setDowned(qaVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setStarCount(qaVoteRepository.countByQaAndStarAndVisible(e, true, true));

                        return p;
                    })
                    .collect(Collectors.toList());
            pojo.setAnswers(answerPojos);
        }

        return pojo;

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
    public QA findByPublicKey(String publicKey, boolean visible) throws DataNotFoundException {
        QA entity = qaRepository.findByPublicKeyAndVisible(publicKey, visible);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        return entity;
    }

    @Override
    public boolean save(String coursePublicKey, QAPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        QA entity = pojoToEntity(pojo);


        if (pojo.isAnswer()) {
            QA parent = findByPublicKey(pojo.getPublicKey(), true);
            entity.setParent(parent);
            entity.setAnswer(true);
        }



        entity.generatePublicKey();
        entity.setCourse(course);
        entity.setCreatedBy(authenticatedUser);

        entity = qaRepository.save(entity);

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
    public boolean update(String coursePublicKey, QAPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        QA entity = findByPublicKey(coursePublicKey, true);

        if (entity == null){
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", coursePublicKey));
        }

        entity.setUpdatedBy(authenticatedUser);
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity = qaRepository.save(entity);

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
    public boolean delete(String coursePublicKey, String publicKey) throws DataNotFoundException, ExecutionFailException {
        QA entity = findByPublicKey(publicKey, true);
        if (entity == null){
            throw new DataNotFoundException("No such a question is found publicKey");
        }
        entity.setVisible(false);
        entity = qaRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new DataNotFoundException("System resource is not deleted by publicKey");
        }
        return true;

    }


    @Override
    public boolean vote(String publicKey, VoteType vote) throws ExecutionFailException, DataNotFoundException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        QA qa = findByPublicKey(publicKey, true);

        QAVote entity = null;
        entity = qaVoteRepository.findByQaAndCreatedBy(qa, authenticatedUser);


        if (entity == null) {
            entity = new QAVote();
            entity.setCreatedBy(authenticatedUser);
            entity.generatePublicKey();
        } else {
            entity.setUpdatedBy(authenticatedUser);
        }


        entity.setQa(qa);

        if (vote.equals(VoteType.UP)) {
            entity.setUp(!entity.isUp());
            entity.setDown(false);

        } else if (vote.equals(VoteType.DOWN)) {
            entity.setUp(false);
            entity.setDown(!entity.isDown());

        } else if (vote.equals(VoteType.STAR)) {
            entity.setStar(!entity.isStar());
        }

        entity = qaVoteRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Not voted");
        }

        return true;
    }
}