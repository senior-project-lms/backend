package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.EnrollmentRequest;
import com.lms.pojos.course.EnrollmentRequestPojo;

import java.util.List;

public interface EnrollmentRequestService {


    EnrollmentRequestPojo entityToPojo(EnrollmentRequest entity);

    EnrollmentRequest pojoToEntity(EnrollmentRequestPojo pojo);

    boolean enroll(String publicKey) throws DataNotFoundException, ExecutionFailException;

    List<EnrollmentRequestPojo> getEnrolmentRequests(String publicKey) throws DataNotFoundException;

    boolean approveEnrolmentRequest(String enrolmentRequestPublicKey) throws DataNotFoundException, ExecutionFailException;

    List<EnrollmentRequest> findEnrollmentRequests(boolean visible) throws DataNotFoundException;

    List<EnrollmentRequest> findEnrollmentRequests(String userPublicKey, boolean visible) throws DataNotFoundException;

    List<EnrollmentRequestPojo> getEnrollmentRequest(boolean visible) throws DataNotFoundException;

    List<EnrollmentRequestPojo> getEnrollmentRequest(String userPublicKey, boolean visible) throws DataNotFoundException;

    boolean cancel(String publicKey) throws DataNotFoundException, ExecutionFailException;

    boolean reject(String publicKey) throws DataNotFoundException, ExecutionFailException;

}
