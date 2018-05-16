package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQAComment;
import com.lms.pojos.course.CourseQACommentPojo;

public interface CourseQACommentService {

    CourseQACommentPojo entityToPojo(CourseQAComment entity);

    CourseQAComment pojoToEntity(CourseQACommentPojo pojo);

    boolean save(String qaPublicKey, CourseQACommentPojo pojo) throws DataNotFoundException, ExecutionFailException;
}
