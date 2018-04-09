package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.QA;
import com.lms.entities.course.QAComment;
import com.lms.pojos.course.QACommentPojo;
import com.lms.repositories.QACommentRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQAService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.course.CourseQACommentService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseQACommentServiceImpl implements CourseQACommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private QACommentRepository qaCommentRepository;

    @Autowired
    private CourseQAService courseQAService;

    @Autowired
    private CourseService courseService;


    @Override
    public QACommentPojo entityToPojo(QAComment entity) {
        QACommentPojo pojo = new QACommentPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        if (!entity.isAnonymous()) {
            pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        }
        return pojo;
    }

    @Override
    public QAComment pojoToEntity(QACommentPojo pojo) {
        QAComment entity = new QAComment();
        entity.setContent(pojo.getContent());
        entity.setQa(entity.getQa());
        return entity;
    }


    @Override
    public boolean save(String qaPublicKey, QACommentPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = userDetailService.getAuthenticatedUser();

        QA qa = courseQAService.findByPublicKey(qaPublicKey, true);

        QAComment entity = pojoToEntity(pojo);
        entity.setCreatedBy(authenticatedUser);
        entity.setQa(qa);

        entity = qaCommentRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Comment is not saved");
        }

        return true;
    }
}


