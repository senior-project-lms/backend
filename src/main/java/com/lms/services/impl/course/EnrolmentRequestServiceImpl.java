package com.lms.services.impl.course;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.EnrolmentRequest;
import com.lms.pojos.course.EnrolmentRequestPojo;
import com.lms.repositories.EnrolmentRequestRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.EnrolmentRequestService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrolmentRequestServiceImpl implements EnrolmentRequestService {

    @Autowired
    private EnrolmentRequestRepository enrolmentRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CustomUserDetailService userDetailService;


    @Override
    public EnrolmentRequestPojo entityToPojo(EnrolmentRequest entity) {
        EnrolmentRequestPojo pojo = new EnrolmentRequestPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setCourse(courseService.entityToPojo(entity.getCourse()));
        pojo.setUser(userService.entityToPojo(entity.getUser()));
        pojo.setUpdatedAt(entity.getUpdatedAt());

        return pojo;
    }

    @Override
    public EnrolmentRequest pojoToEntity(EnrolmentRequestPojo pojo) {
        EnrolmentRequest entity = new EnrolmentRequest();
        return entity;
    }

    @Override
    public boolean enroll(String coursePublicKey) throws DataNotFoundException, ExecutionFailException {

        User enroledBy = userDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        EnrolmentRequest enrolmentRequest = new EnrolmentRequest();

        enrolmentRequest.generatePublicKey();
        enrolmentRequest.setCourse(course);
        enrolmentRequest.setUser(enroledBy);
        enrolmentRequest.setCreatedBy(enroledBy);

        enrolmentRequest = enrolmentRequestRepository.save(enrolmentRequest);

        if (enrolmentRequest == null || enrolmentRequest.getId() == 0) {
            throw new ExecutionFailException("No such a enrolment request is saved");

        }

        return true;
    }

    @Override
    public List<EnrolmentRequestPojo> getEnrolmentRequests(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<EnrolmentRequest> entities = enrolmentRequestRepository.findAllByCourseAndVisible(course, true);


        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }

        List<EnrolmentRequestPojo> pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());

        return pojos;
    }

    @Override
    public boolean approveEnrolmentRequest(String enrolmentRequestPublicKey) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();

        EnrolmentRequest entity = enrolmentRequestRepository.findByPublicKey(enrolmentRequestPublicKey);
        if (entity == null) {
            throw new DataNotFoundException(String.format("No such enrolment request is found by publicKey: %s", enrolmentRequestPublicKey));

        }

        if (!courseService.registerUserToCourse(entity.getCourse(), entity.getUser())) {
            throw new ExecutionFailException("User is not registered to course");
        }

        entity.setUpdatedBy(authUser);
        entity.setVisible(true);
        entity = enrolmentRequestRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("User is not registered to course");
        }

        return true;
    }


}
