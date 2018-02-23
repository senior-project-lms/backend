package com.lms.services.impl.course;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.EnrollmentRequest;
import com.lms.pojos.course.EnrollmentRequestPojo;
import com.lms.repositories.EnrollmentRequestRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.EnrollmentRequestService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentRequestServiceImpl implements EnrollmentRequestService {

    @Autowired
    private EnrollmentRequestRepository enrolmentRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CustomUserDetailService userDetailService;


    @Override
    public EnrollmentRequestPojo entityToPojo(EnrollmentRequest entity) {
        EnrollmentRequestPojo pojo = new EnrollmentRequestPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setCourse(courseService.entityToPojo(entity.getCourse()));
        pojo.setUser(userService.entityToPojo(entity.getUser()));
        pojo.setUpdatedAt(entity.getUpdatedAt());

        return pojo;
    }

    @Override
    public EnrollmentRequest pojoToEntity(EnrollmentRequestPojo pojo) {
        EnrollmentRequest entity = new EnrollmentRequest();
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

        EnrollmentRequest enrolmentRequest = new EnrollmentRequest();

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
     * @return List<EnrollmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequestPojo> getEnrolmentRequests(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<EnrollmentRequest> entities = enrolmentRequestRepository.findAllByCourseAndVisibleAndCancelled(course, true, false);


        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }

        List<EnrollmentRequestPojo> pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());

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

        EnrollmentRequest entity = enrolmentRequestRepository.findByPublicKey(enrolmentRequestPublicKey);

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
     * @return List<EnrollmentRequest>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequest> findEnrollmentRequests(String userPublicKey, boolean visible) throws DataNotFoundException {

        User authUser = userService.findByPublicKey(userPublicKey);
        List<EnrollmentRequest> entities = enrolmentRequestRepository.findAllByUserAndVisibleAndCancelled(authUser, visible, false);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }
        return entities;
    }


    /**
     * finds enrolment request entities by authenticated user and visibility
     *
     * @param visible
     * @return List<EnrollmentRequest>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequest> findEnrollmentRequests(boolean visible) throws DataNotFoundException {

        User authUser = userDetailService.getAuthenticatedUser();
        List<EnrollmentRequest> entities = enrolmentRequestRepository.findAllByUserAndVisibleAndCancelled(authUser, visible, false);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }
        return entities;
    }

    /**
     * finds enrolment request pojos by authenticated user and visibility
     *
     * @param visible
     * @return List<EnrollmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequestPojo> getEnrollmentRequest(boolean visible) throws DataNotFoundException {
        User authUser = userDetailService.getAuthenticatedUser();
        return this.getEnrolmentRequest(authUser, visible);
    }

    /**
     * finds enrolment request pojos by userPublicKey  and visibility
     *
     * @param visible
     * @return List<EnrollmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequestPojo> getEnrollmentRequest(String userPublicKey, boolean visible) throws DataNotFoundException {
        User authUser = userService.findByPublicKey(userPublicKey);
        List<EnrollmentRequestPojo> pojos = this.getEnrolmentRequest(authUser, visible);
        pojos
                .stream()
                .map(pojo -> {
                    pojo.setUser(null);
                    return pojo;
                })
                .collect(Collectors.toList());


        return pojos;
    }

    private List<EnrollmentRequestPojo> getEnrolmentRequest(User user, boolean visible) throws DataNotFoundException {
        List<EnrollmentRequest> entities = enrolmentRequestRepository.findAllByUserAndVisibleAndCancelled(user, visible, false);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrolment request list is fetched");
        }
        List<EnrollmentRequestPojo> pojos = entities
                .stream()
                .map(pojo -> entityToPojo(pojo))
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public boolean cancel(String publicKey) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();

        EnrollmentRequest entity = enrolmentRequestRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException("No such a enrolment request is found");
        }

        entity.setCancelled(true);
        entity.setUpdatedBy(authUser);

        entity = enrolmentRequestRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a enrolment request is cancelled");
        }
        return true;
    }

    @Override
    public boolean reject(String publicKey) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();

        EnrollmentRequest entity = enrolmentRequestRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException("No such a enrolment request is found");
        }

        entity.setRejected(true);
        entity.setUpdatedBy(authUser);

        entity = enrolmentRequestRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a enrolment request is cancelled");
        }
        return true;
    }

    // TODO: 23.02.2018
    // rejectedler listlensin ama enroll requested icindeki course yine aranabilsin ve arama listesinde ciksin, course service de update yap
}
