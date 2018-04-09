package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQA;
import com.lms.entities.GlobalQAVote;
import com.lms.entities.User;
import com.lms.enums.VoteType;
import com.lms.pojos.GlobalQACommentPojo;
import com.lms.pojos.GlobalQAPojo;
import com.lms.repositories.GlobalQARepository;
import com.lms.repositories.GlobalQAVoteRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.GlobalQACommentService;
import com.lms.services.interfaces.GlobalQAService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlobalQAServiceImpl implements GlobalQAService {

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalQACommentService globalQACommentService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private GlobalQARepository globalQARepository;

    @Autowired
    private GlobalQAVoteRepository globalQAVoteRepository;

    /**
     * Converts GlobalQAPojo to GlobalQA according to values, if the value is null passes it,

     * @author emsal aynaci
     * @param pojo
     * @return GlobalQA
     */

    public GlobalQA pojoToEntity(GlobalQAPojo pojo) {

        GlobalQA entity = new GlobalQA();
        entity.setContent(pojo.getContent());
        entity.setTitle(pojo.getTitle());
        entity.setAnonymous(pojo.isAnonymous());
        return entity;
    }

    /**
     * Converts GlobalQA entity to GlobalQuestion pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author emsal aynaci
     * @param entity
     * @return GlobalQAPojo
     */

    public GlobalQAPojo entityToPojo(GlobalQA entity) {

        GlobalQAPojo pojo = new GlobalQAPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setAnonymous(entity.isAnonymous());
        pojo.setAnswer(entity.isAnswer());
        if (!entity.isAnonymous()){
            pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        }
        if (entity.getComments() != null){
            List<GlobalQACommentPojo> comments = entity.getComments()
                    .stream()
                    .map(e -> globalQACommentService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setComments(comments);

        }
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setUpdatedAt(entity.getUpdatedAt());
        return pojo;
    }


    /**
     *
     * Returns a list of 10 GlobalQaQuestionPojos,
     * Selects the visible GlobalQA and converts it to pojo than returns
     *
     * @author emsal aynaci
     * @updatedBy umit.kas
     * @param page
     * @return  List<GlobalQAPojo>
     */

    @Override
    public List<GlobalQAPojo> getAllByPage(int page) throws DataNotFoundException {

        List<GlobalQA> entities = globalQARepository.findAllByVisibleAndAnswer(true, false, new PageRequest(page, 10));

        if (entities == null){
            throw new DataNotFoundException("no such a question is found");
        }
        List<GlobalQAPojo> pojos = entities
                .stream()
                .map(e -> {
                    GlobalQAPojo pojo = entityToPojo(e);
                    pojo.setComments(null);
                    pojo.setUpCount(globalQAVoteRepository.countByQaAndUpAndVisible(e, true, true));
                    pojo.setDownCount(globalQAVoteRepository.countByQaAndDownAndVisible(e, true, true));
                    pojo.setAnswerCount(globalQARepository.countByParentAndVisible(e, true));
                    pojo.setStarCount(globalQAVoteRepository.countByQaAndStarAndVisible(e, true, true));
                    return pojo;
                })
                .collect(Collectors.toList());

        return pojos;
    }



    @Override
    public GlobalQAPojo getByPublicKey(String publicKey) throws DataNotFoundException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        GlobalQAPojo pojo;
        GlobalQA entity = findByPublicKey(publicKey, true);

        List<GlobalQA> answersEntities = globalQARepository.findAllByParentAndVisible(entity, true);

        pojo = entityToPojo(entity);

        pojo.setUpCount(globalQAVoteRepository.countByQaAndUpAndVisible(entity, true, true));
        pojo.setDownCount(globalQAVoteRepository.countByQaAndDownAndVisible(entity, true, true));

        pojo.setStarred(globalQAVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setUpped(globalQAVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setDowned(globalQAVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(entity, true, true, authenticatedUser));
        pojo.setStarCount(globalQAVoteRepository.countByQaAndStarAndVisible(entity, true, true));

        if (answersEntities != null){

            List<GlobalQAPojo> answerPojos = answersEntities
                    .stream()
                    .map(e -> {
                        GlobalQAPojo p =  entityToPojo(e);
                        p.setUpCount(globalQAVoteRepository.countByQaAndUpAndVisible(e, true, true));
                        p.setDownCount(globalQAVoteRepository.countByQaAndDownAndVisible(e, true, true));
                        p.setStarred(globalQAVoteRepository.existsByQaAndStarAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setUpped(globalQAVoteRepository.existsByQaAndUpAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setDowned(globalQAVoteRepository.existsByQaAndDownAndVisibleAndCreatedBy(e, true, true, authenticatedUser));
                        p.setStarCount(globalQAVoteRepository.countByQaAndStarAndVisible(e, true, true));

                        return p;
                    })
                    .collect(Collectors.toList());
            pojo.setAnswers(answerPojos);
        }


        return pojo;
    }


    /**
     *
     * Return the GlobalQA,
     * findBy publicKey
     * return entity

     * @author emsal aynaci
     * @param publicKey
     * @return GlobalQA
     */

    @Override
    public GlobalQA findByPublicKey(String publicKey, boolean visible) throws DataNotFoundException {

        GlobalQA entity = globalQARepository.findByPublicKeyAndVisible(publicKey, visible);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }
        return entity;
    }

    /**
     *
     * Save the GlobalQA, converts input pojo to entity, also converts resources to entity
     * add who creates(authenticated user)
     * generates publicKey
     * save entity

     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */

    @Override
    public boolean save(GlobalQAPojo pojo) throws DataNotFoundException,ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        GlobalQA entity = pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setCreatedBy(authenticatedUser);


        if (pojo.isAnswer()){
            GlobalQA parent = findByPublicKey(pojo.getPublicKey(), true);
            entity.setParent(parent);
            entity.setAnswer(true);
        }


        entity = globalQARepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            if (pojo.isAnswer()){
                throw new ExecutionFailException("No such a answer is saved");
            }

            throw new ExecutionFailException("No such a question is saved");
        }

        return true;
    }


    /**
     *
     * Update the GlobalQA,
     * add who updates it,
     * update values
     * then save it.
     *
     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */

    @Override
    public boolean update(String publicKey, GlobalQAPojo pojo) throws DataNotFoundException,ExecutionFailException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        GlobalQA entity = findByPublicKey(publicKey, true);

        entity.setUpdatedBy(authenticatedUser);
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity = globalQARepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new ExecutionFailException("No such a question is updated");
        }

        return true;

    }


    /**
     *
     * Delete the GlobalQA,
     * Delete means set visible false to property of it.
     * update visibility false and then save it.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return boolean
     */

    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException{
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        GlobalQA entity = findByPublicKey(publicKey, true);
        if (entity == null){
            throw new DataNotFoundException("No such a question is found publicKey");
        }
        entity.setVisible(false);
        entity.setUpdatedBy(authenticatedUser);
        entity = globalQARepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("Question is not deleted by publicKey");
        }
        return true;
    }


    @Override
    public boolean vote(String publicKey, VoteType vote) throws ExecutionFailException, DataNotFoundException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        GlobalQA qa = findByPublicKey(publicKey, true);

        GlobalQAVote entity = null;
        entity = globalQAVoteRepository.findByQaAndCreatedBy(qa, authenticatedUser);


        if (entity == null) {
            entity = new GlobalQAVote();
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

        entity = globalQAVoteRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Not voted");
        }

        return true;
    }
}
