package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.EnrolmentRequest;
import com.lms.pojos.course.EnrolmentRequestPojo;

import java.util.List;

public interface EnrolmentRequestService {


    EnrolmentRequestPojo entityToPojo(EnrolmentRequest entity);

    EnrolmentRequest pojoToEntity(EnrolmentRequestPojo pojo);

    boolean enroll(String publicKey) throws DataNotFoundException, ExecutionFailException;

    List<EnrolmentRequestPojo> getEnrolmentRequests(String publicKey) throws DataNotFoundException;

    boolean approveEnrolmentRequest(String enrolmentRequestPublicKey) throws DataNotFoundException, ExecutionFailException;


}
