package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.CourseQA;
import com.lms.entities.course.CourseQAComment;
import com.lms.pojos.course.CourseQACommentPojo;
import com.lms.repositories.CourseQACommentRepository;
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
    private CourseQACommentRepository courseQaCommentRepository;

    @Autowired
    private CourseQAService courseQAService;

    @Autowired
    private CourseService courseService;


    @Override
    public CourseQACommentPojo entityToPojo(CourseQAComment entity) {
        CourseQACommentPojo pojo = new CourseQACommentPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        if (!entity.isAnonymous()) {
            pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        }
        return pojo;
    }

    @Override
    public CourseQAComment pojoToEntity(CourseQACommentPojo pojo) {
        CourseQAComment entity = new CourseQAComment();
        entity.setContent(pojo.getContent());
        entity.setQa(entity.getQa());
        return entity;
    }


    @Override
    public boolean save(String qaPublicKey, CourseQACommentPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = userDetailService.getAuthenticatedUser();

        CourseQA qa = courseQAService.findByPublicKey(qaPublicKey, true);

        CourseQAComment entity = pojoToEntity(pojo);
        entity.setCreatedBy(authenticatedUser);
        entity.setQa(qa);

        entity = courseQaCommentRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Comment is not saved");
        }

        return true;
    }
}


