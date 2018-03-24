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

    boolean save(GradePojo pojo);

    boolean updateWeight(GradePojo pojo);

    boolean updateName(GradePojo pojo);

    boolean delete(String coursePublicKey, String gradePublicKey);

    Grade findByPublicKeyAndCoursePublicKey(String publicKey, String coursePublicKey) throws DataNotFoundException;
}
