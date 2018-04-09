package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.QA;
import com.lms.pojos.course.QaPojo;

import java.util.List;

public interface QaService {

    QaPojo entityToPojo(QA entity);

    QA pojoToEntity(QaPojo pojo);

    List<QaPojo> getAllByPage(int page) throws DataNotFoundException;

    QaPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    QA findByCoursePublicKey(String publicKey) throws DataNotFoundException;

    boolean save(QaPojo pojo) throws DataNotFoundException,ExecutionFailException;

    boolean update(QaPojo pojo) throws DataNotFoundException,ExecutionFailException;

    boolean delete(String publicKey) throws DataNotFoundException,ExecutionFailException;
}
