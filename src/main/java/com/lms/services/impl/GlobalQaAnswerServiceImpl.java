package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.GlobalQaAnswer;
import com.lms.entities.GlobalQaQuestion;
import com.lms.pojos.GlobalQaAnswerPojo;
import com.lms.repositories.GlobalQaAnswerRepository;
import com.lms.services.interfaces.GlobalQaAnswerService;
import com.lms.services.interfaces.GlobalQaQuestionService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GlobalQaAnswerServiceImpl implements GlobalQaAnswerService {


    @Autowired
    private UserService userService;

    @Autowired
    private GlobalQaAnswerRepository globalQaAnswerRepository;

    @Autowired
    private GlobalQaQuestionService globalQaQuestionService;


    /**
     * Converts GlobalQaAnswer entity to GlobalAnswer pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author emsal aynaci
     * @param entity
     * @return GlobalQaAnswerPojo
     */

    @Override
    public GlobalQaAnswerPojo entityToPojo(GlobalQaAnswer entity) {

       GlobalQaAnswerPojo pojo = new GlobalQaAnswerPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;

    }


    /**
     * Converts GlobalQaAnswerPojo to GlobalQaAnswer according to values, if the value is null passes it,

     * @author emsal aynaci
     * @param pojo
     * @return GlobalQaAnswer
     */

    @Override
    public GlobalQaAnswer pojoToEntity(GlobalQaAnswerPojo pojo)
    {
       GlobalQaAnswer entity = new GlobalQaAnswer();
       entity.setContent(entity.getContent());
       entity.setQuestion(entity.getQuestion());
       return entity;
    }


    @Override
    public List<GlobalQaAnswerPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException {

        GlobalQaQuestion qlobalanswer = globalQaQuestionService.findByPublicKey(questionPublicKey);
        List<GlobalQaAnswer> qaAnswers = globalQaAnswerRepository.findAllByQuestion(qlobalanswer);

        List<GlobalQaAnswerPojo> pojos = new ArrayList<>();
        for (GlobalQaAnswer entity : qaAnswers) {
            pojos.add(entityToPojo(entity));
        }
        return pojos;
    }


}

