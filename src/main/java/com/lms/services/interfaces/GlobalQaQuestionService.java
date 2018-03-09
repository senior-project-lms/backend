package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQaQuestion;
import com.lms.pojos.GlobalQaQuestionPojo;

import java.util.List;

public interface GlobalQaQuestionService {

    GlobalQaQuestionPojo entityToPojo(GlobalQaQuestion entity);

    GlobalQaQuestion pojoToEntity(GlobalQaQuestionPojo pojo);

    List<GlobalQaQuestionPojo> getAllByPage(int page) throws DataNotFoundException;

    GlobalQaQuestionPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    GlobalQaQuestion findByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(GlobalQaQuestionPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean delete(String publicKey) throws DataNotFoundException,ExecutionFailException;

    boolean update(GlobalQaQuestionPojo pojo)throws DataNotFoundException,ExecutionFailException;
}

//findBy entity