package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.QA;
import com.lms.enums.VoteType;
import com.lms.pojos.course.QAPojo;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

public interface CourseQAService {

    QAPojo entityToPojo(QA entity);

    QA pojoToEntity(QAPojo pojo);

    List<QAPojo> getAll(String coursePublicKey, int page) throws DataNotFoundException;

    QAPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    QA findByPublicKey(String publicKey, boolean visible) throws DataNotFoundException;

    boolean save(String coursePublicKey, QAPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean update(String coursePublicKey, QAPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean delete(String coursePublicKey, String publicKey) throws DataNotFoundException, ExecutionFailException;

    boolean vote(String publicKey, VoteType vote) throws ExecutionFailException, DataNotFoundException;

}
