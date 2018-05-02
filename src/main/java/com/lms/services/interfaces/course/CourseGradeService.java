package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Grade;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.GradePojo;

import java.util.List;

public interface CourseGradeService {


    GradePojo entityToPojo(Grade entity);

    Grade pojoToEntity(GradePojo pojo);

    List<GradePojo> getAll(String coursePublicKey) throws DataNotFoundException;

    GradePojo get(String publicKey) throws DataNotFoundException;

    GradePojo getForView(String publicKey) throws DataNotFoundException;

    SuccessPojo save(String coursePublicKey, GradePojo pojo) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo update(String publicKey, GradePojo pojo) throws DataNotFoundException, ExecutionFailException;

    SuccessPojo delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

    Grade findByPublicKey(String publicKey) throws DataNotFoundException;

    List<Grade> findAll(String coursePublicKey) throws DataNotFoundException;


    boolean publish(String publicKey, boolean status) throws DataNotFoundException, ExecutionFailException;

}
