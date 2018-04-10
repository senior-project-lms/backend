package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.CourseQAComment;
import com.lms.pojos.course.QACommentPojo;

public interface CourseQACommentService {

    QACommentPojo entityToPojo(CourseQAComment entity);

    CourseQAComment pojoToEntity(QACommentPojo pojo);

    boolean save(String qaPublicKey, QACommentPojo pojo) throws DataNotFoundException, ExecutionFailException;
}
