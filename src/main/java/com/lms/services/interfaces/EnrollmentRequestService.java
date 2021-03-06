package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.customExceptions.NotAuthenticatedRequest;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.EnrollmentRequest;
import com.lms.pojos.course.EnrollmentRequestPojo;

import java.util.List;
import java.util.Map;

public interface EnrollmentRequestService {


    EnrollmentRequestPojo entityToPojo(EnrollmentRequest entity);

    EnrollmentRequest pojoToEntity(EnrollmentRequestPojo pojo);

    boolean enroll(String publicKey, boolean observer) throws DataNotFoundException, ExecutionFailException, ExistRecordException;

    boolean approve(String enrolmentRequestPublicKey) throws DataNotFoundException, ExecutionFailException, ExistRecordException;

    boolean approveAll(List<String> enrollmentRequestPublicKeys) throws DataNotFoundException, ExecutionFailException;

    List<EnrollmentRequest> findEnrollmentRequestsOfAuthUser(boolean visible) throws DataNotFoundException;

    List<EnrollmentRequest> findEnrollmentRequestsOfUser(String userPublicKey, boolean visible) throws DataNotFoundException;

    List<EnrollmentRequestPojo> getEnrollmentRequestOfAuthUser(boolean visible) throws DataNotFoundException;

    List<EnrollmentRequestPojo> getEnrollmentRequestOfUser(String userPublicKey, boolean visible) throws DataNotFoundException;

    boolean cancel(String publicKey) throws DataNotFoundException, ExecutionFailException, NotAuthenticatedRequest;

    boolean reject(String publicKey) throws DataNotFoundException, ExecutionFailException, ExistRecordException;

    boolean existByPublicKeyAndUser(String publicKey, User user);


    List<EnrollmentRequestPojo> getEnrollmentRequestsOfCourse(String publicKey) throws DataNotFoundException;


    boolean updateVisibilityByCourse(Course course, boolean visibility) throws DataNotFoundException;

    Map<String, Integer> getRequestCountsOfCourse(String publicKey);

    EnrollmentRequest findByCourseAndUserPublicKeys(String coursePublicKey, String userPublicKeys) throws DataNotFoundException;

    boolean enrollByAdmin(String coursePublicKey, String userPublicKey) throws ExistRecordException, ExecutionFailException, DataNotFoundException ;
}
