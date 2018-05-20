package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQA;
import com.lms.entities.course.CourseQATag;
import com.lms.entities.course.CourseQAVote;
import com.lms.enums.VoteType;
import com.lms.pojos.course.CourseQAPojo;
import com.lms.pojos.course.CourseQATagPojo;
import com.lms.pojos.course.CourseQACommentPojo;
import com.lms.repositories.CourseQARepository;
import com.lms.repositories.CourseQAVoteRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQACommentService;
import com.lms.services.interfaces.course.CourseQAService;
import com.lms.services.interfaces.course.CourseQATagService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQAServiceImpl implements CourseQAService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseQARepository courseQaRepository;

    @Autowired
    private CourseService courseService;


    @Autowired
    private CourseQAVoteRepository courseQaVoteRepository;


    @Autowired
    private CourseQACommentService courseQACommentService;

    @Autowired
    private CourseQATagService courseQATagService;

    /**
     * Converts CourseQAPojo to CourseQA according to values, if the value is null passes it,
     *
     * @param pojo
     * @return CourseQA
     * @author emsal aynaci
     */

    public CourseQA pojoToEntity(CourseQAPojo pojo) {

        CourseQA entity = new CourseQA();
        entity.setContent(pojo.getContent());
        entity.setTitle(pojo.getTitle());
        return entity;
    }

    /**
     * Converts CourseQA entity to Question pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param entity
     * @return CourseQAPojo
     * @author emsal aynaci
     */

    public CourseQAPojo entityToPojo(CourseQA entity) {

        CourseQAPojo pojo = new CourseQAPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());

        if (!entity.isAnonymous()) {
            pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        }
        if (entity.getComments() != null) {
            List<CourseQACommentPojo> comments = entity.getComments()
                    .stream()
                    .map(e -> courseQACommentService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setComments(comments);
        }
        if (entity.getTags() != null) {
            List<CourseQATagPojo> tags = entity.getTags()
                    .stream()
                    .map(e -> courseQATagService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setTags(tags);
        }
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setUpdatedAt(entity.getUpdatedAt());
        return pojo;
    }


    /**
     *
     * Returns a list of 10 QaQuestionPojos,
     * Selects the visible CourseQA and converts it to pojo than returns
     *
     * @author emsal aynaci
     * @param page
     * @return List<CourseQAPojo>
     */

    @Override
    public List<CourseQAPojo> getAll(String coursePublicKey, int page) throws DataNotFoundException {

        Course course = courseService.findByPublicKey(coursePublicKey);

        List<CourseQA> entities = courseQaRepository.findAllByCourseAndAnswerAndVisibleOrderByCreatedAtDesc(course, false, true, new PageRequest(page, 10));

        if (entities == null){
            throw new DataNotFoundException("no such a question is found");
        }

        List<CourseQAPojo> pojos = entities
                .stream()
                .map(e -> {
                    CourseQAPojo pojo = entityToPojo(e);
                    pojo.setComments(null);
                    pojo.setAnswers(null);
                    pojo.setUpCount(courseQaVoteRepository.countByQaAndUpAndVisible(e, true, true));
                    pojo.setDownCount(courseQaVoteRepository.countByQaAndDownAndVisible(e, true, true));
                    pojo.setAnswerCount(courseQaRepository.countByParentAndVisible(e, true));
                    pojo.setStarCount(courseQaVoteRepository.countByQaAndStarAndVisible(e, true, true));
                    return pojo;
                })
                .collect(Collectors.toList());
        return pojos;
    }


    @Override
    public CourseQAPojo getByPublicKey(String publicKey) throws DataNotFoundException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        CourseQAPojo pojo;
        CourseQA entity = findByPublicKey(publicKey, true);

        List<CourseQA> answersEntities = courseQaRepository.findAllByParentAndVisible(entity, true);

        pojo = entityToPojo(entity);

        pojo.setUpCount(courseQaVoteRepository.countByQaAndUpAndVisible(entity, true, true));
        pojo.setDownCount(courseQaVoteRepository.countByQaAndDownAndVisible(entity, true, true));

        pojo.setStarred(courseQaVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setUpped(courseQaVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setDowned(courseQaVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setStarCount(courseQaVoteRepository.countByQaAndStarAndVisible(entity, true, true));

        if (answersEntities != null){

            List<CourseQAPojo> answerPojos = answersEntities
                    .stream()
                    .map(e -> {
                        CourseQAPojo p = entityToPojo(e);
                        p.setUpCount(courseQaVoteRepository.countByQaAndUpAndVisible(e, true, true));
                        p.setDownCount(courseQaVoteRepository.countByQaAndDownAndVisible(e, true, true));
                        p.setStarred(courseQaVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setUpped(courseQaVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setDowned(courseQaVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setStarCount(courseQaVoteRepository.countByQaAndStarAndVisible(e, true, true));

                        return p;
                    })
                    .collect(Collectors.toList());
            pojo.setAnswers(answerPojos);
        }

        return pojo;

    }


    /**
     *
     * Return the CourseQA,
     * findBy Course publicKey
     * return entity

     * @author emsal aynaci
     * @param publicKey
     * @return CourseQA
     */
    @Override
    public CourseQA findByPublicKey(String publicKey, boolean visible) throws DataNotFoundException {
        CourseQA entity = courseQaRepository.findByPublicKeyAndVisible(publicKey, visible);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        return entity;
    }

    @Override
    public boolean save(String coursePublicKey, CourseQAPojo pojo) throws DataNotFoundException, ExecutionFailException, EmptyFieldException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        CourseQA entity = pojoToEntity(pojo);


        if (pojo.isAnswer()) {
            CourseQA parent = findByPublicKey(pojo.getPublicKey(), true);
            entity.setParent(parent);
            entity.setAnswer(true);
        }

        if (pojo.getTags() != null) {
            List<String> publicKeys = pojo.getTags()
                    .stream()
                    .filter(pojoTag -> pojoTag.getPublicKey() != null)
                    .map(pojoTag -> pojoTag.getPublicKey())
                    .collect(Collectors.toList());


            List<CourseQATagPojo> notSavedTags = pojo.getTags()
                    .stream()
                    .filter(pojoTag -> pojoTag.getPublicKey() == null)
                    .collect(Collectors.toList());

            List<CourseQATag> tagsEntities;

            if (!notSavedTags.isEmpty()) {
                tagsEntities = courseQATagService.save(notSavedTags);
                entity.setTags(tagsEntities);

            }
            if (!publicKeys.isEmpty()) {
                tagsEntities = courseQATagService.findAllByPublicKeys(publicKeys);
                if (entity.getTags() != null) {
                    entity.getTags().addAll(tagsEntities);
                } else {
                    entity.setTags(tagsEntities);
                }

            }
        }

        entity.generatePublicKey();
        entity.setCourse(course);
        entity.setCreatedBy(authenticatedUser);

        entity = courseQaRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new ExecutionFailException("No such a question is saved");
        }

        return true;
    }


    /**
     *
     * Update the CourseQA,
     * add who updates it,
     * update values
     * then save it.
     *
     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */
    @Override
    public boolean update(String coursePublicKey, CourseQAPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        CourseQA entity = findByPublicKey(coursePublicKey, true);

        if (entity == null){
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", coursePublicKey));
        }

        entity.setUpdatedBy(authenticatedUser);
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity = courseQaRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new DataNotFoundException("No such a question is updated");
        }

        return true;

    }

    /**
     *
     * Delete the CourseQA,
     * Delete means set visible false to property of it.
     * update visibility false and then save it.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return boolean
     */
    @Override
    public boolean delete(String coursePublicKey, String publicKey) throws DataNotFoundException, ExecutionFailException {
        CourseQA entity = findByPublicKey(publicKey, true);
        if (entity == null){
            throw new DataNotFoundException("No such a question is found publicKey");
        }
        entity.setVisible(false);
        entity = courseQaRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new DataNotFoundException("System resource is not deleted by publicKey");
        }
        return true;

    }


    @Override
    public boolean vote(String publicKey, VoteType vote) throws ExecutionFailException, DataNotFoundException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        CourseQA qa = findByPublicKey(publicKey, true);

        CourseQAVote entity = null;
        entity = courseQaVoteRepository.findByQaAndCreatedBy(qa, authenticatedUser);


        if (entity == null) {
            entity = new CourseQAVote();
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

        entity = courseQaVoteRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Not voted");
        }

        return true;
    }


    @Override
    public List<CourseQAPojo> getTop10RelatedTopics(String publicKey) throws DataNotFoundException {
        CourseQA entity = findByPublicKey(publicKey, true);


        if (entity.getTags() != null) {
            List<CourseQA> entities = courseQaRepository.findTop10ByTagsInAndCourseAndVisibleOrderByCreatedAtDesc(entity.getTags(), entity.getCourse(), true);
            if (entities == null) {
                throw new DataNotFoundException("no such a question is found");
            }
            List<CourseQAPojo> pojos = entities
                    .stream()
                    .filter(e -> !e.getPublicKey().equals(publicKey))
                    .map(e -> {
                        CourseQAPojo pojo = entityToPojo(e);
                        pojo.setComments(null);
                        pojo.setAnswers(null);
                        pojo.setUpCount(courseQaVoteRepository.countByQaAndUpAndVisible(e, true, true));
                        pojo.setDownCount(courseQaVoteRepository.countByQaAndDownAndVisible(e, true, true));
                        return pojo;
                    })
                    .collect(Collectors.toList());

            return pojos;
        }


        return new ArrayList<>();
    }
}