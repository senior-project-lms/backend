package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQA;
import com.lms.enums.VoteType;
import com.lms.pojos.course.CourseQAPojo;

import java.util.List;

public interface CourseQAService {

    CourseQAPojo entityToPojo(CourseQA entity);

    CourseQA pojoToEntity(CourseQAPojo pojo);

    List<CourseQAPojo> getAll(String coursePublicKey, int page) throws DataNotFoundException;

    CourseQAPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    CourseQA findByPublicKey(String publicKey, boolean visible) throws DataNotFoundException;

    boolean save(String coursePublicKey, CourseQAPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean update(String coursePublicKey, CourseQAPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean delete(String coursePublicKey, String publicKey) throws DataNotFoundException, ExecutionFailException;

    boolean vote(String publicKey, VoteType vote) throws ExecutionFailException, DataNotFoundException;

    List<CourseQAPojo> getTop10RelatedTopics(String publicKey) throws DataNotFoundException;

}
