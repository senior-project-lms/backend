package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.Grade;
import com.lms.pojos.course.GradePojo;

import java.util.List;

public interface CourseGradeService {


    GradePojo entityToPojo(Grade entity);

    Grade pojoToEntity(GradePojo pojo);

    List<GradePojo> getAllGradesOfCourse(String coursePublicKey) throws DataNotFoundException;

    GradePojo getGradeOfCourse(String coursePublicKey, String gradePublicKey) throws DataNotFoundException;

    boolean save(String coursePublicKey, GradePojo pojo) throws DataNotFoundException;

    boolean updateWeight(String coursePublicKey, GradePojo pojo);

    boolean updateName(String coursePublicKey, GradePojo pojo);

    boolean delete(String coursePublicKey, String gradePublicKey);

    Grade findByPublicKeyAndCoursePublicKey(String publicKey, String coursePublicKey) throws DataNotFoundException;

    List<Grade> findAllGradesOfCourse(String coursePublicKey) throws DataNotFoundException;

}
