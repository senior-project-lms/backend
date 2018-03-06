package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.QaQuestion;
import com.lms.pojos.course.QaQuestionPojo;

import java.util.List;

public interface QaQuestionService {

    QaQuestionPojo entityToPojo(QaQuestion entity);

    QaQuestion pojoToEntity(QaQuestionPojo pojo);

    List<QaQuestionPojo> getAllByPage(int page) throws DataNotFoundException;

    QaQuestionPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    QaQuestion findByCoursePublicKey(String publicKey) throws DataNotFoundException;

    boolean save(QaQuestionPojo pojo) throws DataNotFoundException,ExecutionFailException;

    boolean update(QaQuestionPojo pojo) throws DataNotFoundException,ExecutionFailException;

    boolean delete(String publicKey) throws DataNotFoundException,ExecutionFailException;
}
