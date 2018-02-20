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


    /**
     * authenticated user enroll the co
     *
     * @param coursePublicKey
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean enroll(String coursePublicKey) throws DataNotFoundException, ExecutionFailException {

        User enrolledBy = userDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        EnrolmentRequest enrolmentRequest = new EnrolmentRequest();

        enrolmentRequest.generatePublicKey();
        enrolmentRequest.setCourse(course);
        enrolmentRequest.setUser(enrolledBy);
        enrolmentRequest.setCreatedBy(enrolledBy);

        enrolmentRequest = enrolmentRequestRepository.save(enrolmentRequest);

        if (enrolmentRequest == null || enrolmentRequest.getId() == 0) {
            throw new ExecutionFailException("No such a enrolment request is saved");

        }

        return true;
    }

    /**
     * returns enrollment request of specific course
     *
     * @param publicKey
     * @return List<EnrolmentRequestPojo>
     * @author umit.kas
     */
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

    /**
     * approves enrollment request by publicKey
     * add user to course
     *
     * @param enrolmentRequestPublicKey
     * @return boolean
     * @author umit.kas
     */
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
        entity.setVisible(false);
        entity = enrolmentRequestRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("User is not registered to course");
        }

        return true;
    }


    /**
     * finds enrolment request entities by userPublicKey and visibility
     *
     * @param userPublicKey
     * @param visible
     * @return List<EnrolmentRequest>
     * @author umit.kas
     */
    @Override
    public List<EnrolmentRequest> findEnrollmentRequests(String userPublicKey, boolean visible) throws DataNotFoundException {

        User authUser = userService.findByPublicKey(userPublicKey);
        List<EnrolmentRequest> entities = enrolmentRequestRepository.findAllByUserAndVisible(authUser, visible);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }
        return entities;
    }


    /**
     * finds enrolment request entities by authenticated user and visibility
     *
     * @param visible
     * @return List<EnrolmentRequest>
     * @author umit.kas
     */
    @Override
    public List<EnrolmentRequest> findEnrollmentRequests(boolean visible) throws DataNotFoundException {

        User authUser = userDetailService.getAuthenticatedUser();
        List<EnrolmentRequest> entities = enrolmentRequestRepository.findAllByUserAndVisible(authUser, visible);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }
        return entities;
    }

    /**
     * finds enrolment request pojos by authenticated user and visibility
     *
     * @param visible
     * @return List<EnrolmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrolmentRequestPojo> getEnrollmentRequest(boolean visible) throws DataNotFoundException {
        User authUser = userDetailService.getAuthenticatedUser();
        return this.getEnrolmentRequest(authUser, visible);
    }

    /**
     * finds enrolment request pojos by userPublicKey  and visibility
     *
     * @param visible
     * @return List<EnrolmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrolmentRequestPojo> getEnrollmentRequest(String userPublicKey, boolean visible) throws DataNotFoundException {
        User authUser = userService.findByPublicKey(userPublicKey);
        return this.getEnrolmentRequest(authUser, visible);
    }

    private List<EnrolmentRequestPojo> getEnrolmentRequest(User user, boolean visible) throws DataNotFoundException {
        List<EnrolmentRequest> entities = enrolmentRequestRepository.findAllByUserAndVisible(user, visible);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }
        List<EnrolmentRequestPojo> pojos = entities
                .stream()
                .map(pojo -> entityToPojo(pojo))
                .collect(Collectors.toList());

        return pojos;

    }
}
