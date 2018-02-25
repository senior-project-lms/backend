package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.customExceptions.NotAuthenticatedRequest;
import com.lms.pojos.course.EnrollmentRequestPojo;
import com.lms.services.interfaces.EnrollmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"/api"})
public class EnrollmentRequestController {


    @Autowired
    private EnrollmentRequestService enrollmentRequestService;

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).ENROLL_COURSE.CODE)")
    @PostMapping("/course/{publicKey}/enroll")
    public boolean enroll(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.enroll(publicKey);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_ENROLLMENT_REQUESTS.CODE)")
    //@PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.EPrivilege).READ_ENROLLMENT_REQUESTS.CODE)")
    @GetMapping("/course/{publicKey}/enrollment-requests")
    public List<EnrollmentRequestPojo> getEnrolmentRequestsOfCourse(@PathVariable String publicKey) throws DataNotFoundException {
        return enrollmentRequestService.getEnrollmentRequestsOfCourse(publicKey);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).APPROVE_ENROLLMENT_REQUEST.CODE)")
    //@PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.EPrivilege).APPROVE_ENROLLMENT_REQUEST.CODE)")
    @PostMapping("/enrollment-request/{enrollmentRequestPublicKey}/approve")
    public boolean approveEnrollmentRequest(@PathVariable String enrollmentRequestPublicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.approve(enrollmentRequestPublicKey);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_REQUESTED_ENROLLMENT_REQUESTS.CODE)")
    //@PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.EPrivilege).READ_REQUESTED_ENROLLMENT_REQUESTS.CODE)")
    @GetMapping("/me/enrollment-requests")
    public List<EnrollmentRequestPojo> getEnrolmentRequestsByVisibilityTrue() throws DataNotFoundException {
        return enrollmentRequestService.getEnrollmentRequestOfAuthUser(true);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).CANCEL_ENROLLMENT_REQUEST.CODE)")
    @PostMapping("/enrollment-request/{publicKey}/cancel")
    boolean cancel(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, NotAuthenticatedRequest {
        return enrollmentRequestService.cancel(publicKey);
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).REJECT_ENROLLMENT_REQUEST.CODE)")
    @PostMapping("/enrollment-request/{publicKey}/reject")
    boolean reject(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.reject(publicKey);
    }

}
