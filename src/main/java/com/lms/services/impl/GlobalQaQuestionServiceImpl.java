package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQaAnswer;
import com.lms.entities.GlobalQaQuestion;
import com.lms.entities.User;
import com.lms.pojos.GlobalQaAnswerPojo;
import com.lms.pojos.GlobalQaQuestionPojo;
import com.lms.repositories.GlobalQaQuestionRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.GlobalQaAnswerService;
import com.lms.services.interfaces.GlobalQaQuestionService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GlobalQaQuestionServiceImpl implements GlobalQaQuestionService {

    @Autowired
    private UserService userService;

    @Autowired
    private GlobalQaAnswerService globalQaAnswerService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private GlobalQaQuestionRepository globalQaQuestionRepository;


    /**
     * Converts GlobalQaQuestionPojo to GlobalQaQuestion according to values, if the value is null passes it,

     * @author emsal aynaci
     * @param pojo
     * @return GlobalQaQuestion
     */

    public GlobalQaQuestion pojoToEntity(GlobalQaQuestionPojo pojo) {

        GlobalQaQuestion entity = new GlobalQaQuestion();
        entity.setContent(pojo.getContent());
        entity.setTitle(pojo.getTitle());

        return entity;
    }

    /**
     * Converts GlobalQaQuestion entity to GlobalQuestion pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author emsal aynaci
     * @param entity
     * @return GlobalQaQuestionPojo
     */

    public GlobalQaQuestionPojo entityToPojo(GlobalQaQuestion entity) {

        GlobalQaQuestionPojo pojo = new GlobalQaQuestionPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;
    }


    /**
     *
     * Returns a list of 10 GlobalQaQuestionPojos,
     * Selects the visible GlobalQaQuestion and converts it to pojo than returns
     *
     * @author emsal aynaci
     * @param page
     * @return  List<GlobalQaQuestionPojo>
     */

    @Override
    public List<GlobalQaQuestionPojo> getAllByPage(int page) throws DataNotFoundException {
        List<GlobalQaQuestionPojo> pojos = new ArrayList<>();

        List<GlobalQaQuestion> entities = globalQaQuestionRepository.findAllByVisible(true,new PageRequest(page,10));

        if (entities == null){
            throw new DataNotFoundException("no such a question is found");
        }

        GlobalQaQuestionPojo pojo;
        for (GlobalQaQuestion entity : entities) {
            pojo = entityToPojo(entity);
            pojos.add(pojo);

        }
        return pojos;
    }

    @Override
    public GlobalQaQuestionPojo getByPublicKey(String publicKey) throws DataNotFoundException {
        GlobalQaQuestionPojo pojo;
        GlobalQaQuestion entity = globalQaQuestionRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }
        pojo = entityToPojo(entity);
       /* List<GlobalQaAnswerPojo> answers = entity.getAnswers()
                .stream()
                .map(answerEntity -> globalQaAnswerService.entityToPojo(answerEntity))
                .collect(Collectors.toList());*/

       List<GlobalQaAnswerPojo> answers = new ArrayList<>();
       for(GlobalQaAnswer answer: entity.getAnswers())
       {
           answers.add(globalQaAnswerService.entityToPojo(answer));

       }
        pojo.setAnswers(answers);
        return pojo;
    }


    /**
     *
     * Return the GlobalQaQuestion,
     * findBy publicKey
     * return entity

     * @author emsal aynaci
     * @param publicKey
     * @return GlobalQaQuestion
     */

    @Override
    public GlobalQaQuestion findByPublicKey(String publicKey) throws DataNotFoundException {

        GlobalQaQuestion entity = globalQaQuestionRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        return entity;
    }

    /**
     *
     * Save the GlobalQaQuestion, converts input pojo to entity, also converts resources to entity
     * add who creates(authenticated user)
     * generates publicKey
     * save entity

     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */

    @Override
    public boolean save(GlobalQaQuestionPojo pojo) throws DataNotFoundException,ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        GlobalQaQuestion entity = pojoToEntity(pojo);
        entity.generatePublicKey();

        entity.setCreatedBy(authenticatedUser);
        entity = globalQaQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new ExecutionFailException("No such a question is saved");
        }

        return true;
    }


    /**
     *
     * Update the GlobalQaQuestion,
     * add who updates it,
     * update values
     * then save it.
     *
     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */

    @Override
    public boolean update(GlobalQaQuestionPojo pojo) throws DataNotFoundException,ExecutionFailException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        String publicKey = pojo.getPublicKey();
        GlobalQaQuestion entity = globalQaQuestionRepository.findByPublicKey(publicKey);
        if (entity == null){
            throw new DataNotFoundException(String.format("No such a question is found for publicKey: %s", publicKey));
        }

        entity.setUpdatedBy(authenticatedUser);
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity = globalQaQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new DataNotFoundException("No such a question is updated");
        }

        return true;

    }


    /**
     *
     * Delete the GlobalQaQuestion,
     * Delete means set visible false to property of it.
     * update visibility false and then save it.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return boolean
     */

    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException{

        GlobalQaQuestion entity = globalQaQuestionRepository.findByPublicKey(publicKey);
        if (entity == null){
            throw new DataNotFoundException("No such a question is found publicKey");
        }
        entity.setVisible(false);
        entity = globalQaQuestionRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new DataNotFoundException("System resource is not deleted by publicKey");
        }
        return true;
    }


}
