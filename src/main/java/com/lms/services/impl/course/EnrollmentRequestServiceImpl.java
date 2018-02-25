package com.lms.services.impl.course;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.customExceptions.NotAuthenticatedRequest;
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
    private EnrollmentRequestRepository enrollmentRequestRepository;

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
        pojo.setRejected(entity.isRejected());
        pojo.setCancelled(entity.isCancelled());
        pojo.setEnrolled(entity.isEnrolled());

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
    public boolean enroll(String coursePublicKey) throws DataNotFoundException, ExecutionFailException, ExistRecordException {

        User enrolledBy = userDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);


        if (enrollmentRequestRepository.existsByUserAndCourseAndRejectedAndCancelledAndEnrolled(enrolledBy, course, false, false, false)) {
            throw new ExistRecordException(String.format("Already have a request for course %s", course.getCode()));
        }


        EnrollmentRequest enrollmentRequest = new EnrollmentRequest();

        enrollmentRequest.generatePublicKey();
        enrollmentRequest.setCourse(course);
        enrollmentRequest.setUser(enrolledBy);
        enrollmentRequest.setCreatedBy(enrolledBy);

        enrollmentRequest = enrollmentRequestRepository.save(enrollmentRequest);

        if (enrollmentRequest == null || enrollmentRequest.getId() == 0) {
            throw new ExecutionFailException("No such a enrollment request is saved");

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
    public List<EnrollmentRequestPojo> getEnrollmentRequestsOfCourse(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<EnrollmentRequest> entities = enrollmentRequestRepository.findAllByCourse(course);


        if (entities == null) {
            throw new DataNotFoundException("No such a enrollment request list is fetched");
        }

        List<EnrollmentRequestPojo> pojos = entities
                .stream()
                .map(entity -> {
                    EnrollmentRequestPojo pojo = entityToPojo(entity);
                    pojo.setCourse(null);
                    pojo.getUser().setAuthority(null);
                    return pojo;
                })
                .collect(Collectors.toList());

        return pojos;
    }

    /**
     * approves enrollment request by publicKey
     * add user to course
     *
     * @param enrollmentRequestPublicKey
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean approve(String enrollmentRequestPublicKey) throws DataNotFoundException, ExecutionFailException, ExistRecordException {

        User authUser = userDetailService.getAuthenticatedUser();

        EnrollmentRequest entity = enrollmentRequestRepository.findByPublicKey(enrollmentRequestPublicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such enrollment request is found by publicKey: %s", enrollmentRequestPublicKey));

        }


        if (entity.getCourse().getRegisteredUsers().contains(entity.getUser())) {
            throw new ExistRecordException("User is already enrolled the couse");
        } else if (!courseService.registerUserToCourse(entity.getCourse(), entity.getUser())) {
            throw new ExecutionFailException("User is not registered to course");
        }


        entity.setUpdatedBy(authUser);
        entity.setVisible(false);
        entity.setEnrolled(true);
        entity = enrollmentRequestRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("User is not registered to course");
        }

        return true;
    }


    /**
     * finds enrollment request entities by userPublicKey and visibility
     *
     * @param userPublicKey
     * @param visible
     * @return List<EnrollmentRequest>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequest> findEnrollmentRequestsOfUser(String userPublicKey, boolean visible) throws DataNotFoundException {

        User authUser = userService.findByPublicKey(userPublicKey);
        List<EnrollmentRequest> entities = enrollmentRequestRepository.findAllByUserAndVisibleAndCancelled(authUser, visible, false);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrollment request list is fetched");
        }
        return entities;
    }

    @Override
    public List<EnrollmentRequest> findEnrollmentRequestsOfAuthUser(boolean visible) throws DataNotFoundException {

        User authUser = userDetailService.getAuthenticatedUser();
        List<EnrollmentRequest> entities = enrollmentRequestRepository.findAllByUserAndVisibleAndCancelled(authUser, visible, false);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrollment request list is fetched");
        }
        return entities;
    }


    /**
     * finds enrollment request pojos by authenticated user and visibility
     *
     * @param visible
     * @return List<EnrollmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequestPojo> getEnrollmentRequestOfAuthUser(boolean visible) throws DataNotFoundException {
        User authUser = userDetailService.getAuthenticatedUser();
        return this.getEnrollmentRequest(authUser, visible);
    }

    /**
     * returns enrollment request pojos by userPublicKey  and visibility
     *
     * @param visible
     * @return List<EnrollmentRequestPojo>
     * @author umit.kas
     */
    @Override
    public List<EnrollmentRequestPojo> getEnrollmentRequestOfUser(String userPublicKey, boolean visible) throws DataNotFoundException {
        User authUser = userService.findByPublicKey(userPublicKey);
        List<EnrollmentRequestPojo> pojos = this.getEnrollmentRequest(authUser, visible);
        pojos
                .stream()
                .map(pojo -> {
                    pojo.setUser(null);
                    return pojo;
                })
                .collect(Collectors.toList());


        return pojos;
    }


    /**
     * renturs enrollment request pojos by user  and visibility
     *
     * @param user
     * @param visible
     * @return List<EnrollmentRequestPojo>
     * @author umit.kas
     */
    private List<EnrollmentRequestPojo> getEnrollmentRequest(User user, boolean visible) throws DataNotFoundException {
        List<EnrollmentRequest> entities = enrollmentRequestRepository.findAllByUserAndVisibleAndCancelled(user, visible, false);
        if (entities == null) {
            throw new DataNotFoundException("No such a enrollment request list is fetched");
        }
        List<EnrollmentRequestPojo> pojos = entities
                .stream()
                .map(pojo -> entityToPojo(pojo))
                .collect(Collectors.toList());

        return pojos;
    }


    /**
     * cancels enrollment request
     *
     *
     * @param publicKey
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean cancel(String publicKey) throws DataNotFoundException, ExecutionFailException, NotAuthenticatedRequest {

        User authUser = userDetailService.getAuthenticatedUser();

        if (!enrollmentRequestRepository.existsByPublicKeyAndUser(publicKey, authUser)) {
            throw new NotAuthenticatedRequest("Not authenticated request");
        }

        EnrollmentRequest entity = enrollmentRequestRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException("No such a enrollment request is found");
        }

        entity.setCancelled(true);
        entity.setUpdatedBy(authUser);

        entity = enrollmentRequestRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a enrollment request is cancelled");
        }
        return true;
    }

    @Override
    public boolean reject(String publicKey) throws DataNotFoundException, ExecutionFailException, ExistRecordException {

        User authUser = userDetailService.getAuthenticatedUser();

        EnrollmentRequest entity = enrollmentRequestRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException("No such a enrollment request is found");
        }

        if (entity.isCancelled() || entity.isEnrolled()) {
            throw new ExistRecordException("Already cancelled or approved, cannot reject request");
        }

        entity.setRejected(true);
        entity.setUpdatedBy(authUser);
        entity.setVisible(false);
        entity = enrollmentRequestRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a enrollment request is cancelled");
        }
        return true;
    }

    @Override
    public boolean existByPublicKeyAndUser(String publicKey, User user) {
        return enrollmentRequestRepository.existsByPublicKeyAndUser(publicKey, user);
    }


    // TODO: 23.02.2018
    // rejectedler listlensin ama enroll requested icindeki course yine aranabilsin ve arama listesinde ciksin, course service de update yap
}
