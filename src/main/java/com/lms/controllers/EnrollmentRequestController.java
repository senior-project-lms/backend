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
import java.util.Map;


@RestController
@RequestMapping(value = {"/api"})
public class EnrollmentRequestController {


    @Autowired
    private EnrollmentRequestService enrollmentRequestService;

    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).ENROLL_COURSE.CODE)")
    @PostMapping("/course/{publicKey}/enroll")
    public boolean enroll(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.enroll(publicKey, false);
    }

    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).ENROLL_COURSE.CODE)")
    @PostMapping("/course/{publicKey}/enroll-as-observer")
    public boolean enrollAsObserver(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.enroll(publicKey, true);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_ENROLLMENT_REQUESTS)")
    @GetMapping("/course/{publicKey}/enrollment-requests")
    public List<EnrollmentRequestPojo> getEnrolmentRequestsOfCourse(@PathVariable String publicKey) throws DataNotFoundException {
        return enrollmentRequestService.getEnrollmentRequestsOfCourse(publicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#coursePublicKey, T(com.lms.enums.ECoursePrivilege).APPROVE_ENROLLMENT_REQUEST)")
    @PostMapping("/course/{coursePublicKey}/enrollment-request/{enrollmentRequestPublicKey}/approve")
    public boolean approveEnrollmentRequest(@PathVariable String coursePublicKey, @PathVariable String enrollmentRequestPublicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.approve(enrollmentRequestPublicKey);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).READ_REQUESTED_ENROLLMENT_REQUESTS.CODE)")
    @GetMapping("/me/enrollment-requests")
    public List<EnrollmentRequestPojo> getEnrolmentRequestsByVisibilityTrue() throws DataNotFoundException {
        return enrollmentRequestService.getEnrollmentRequestOfAuthUser(true);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).CANCEL_ENROLLMENT_REQUEST.CODE)")
    @PostMapping("/enrollment-request/{publicKey}/cancel")
    boolean cancel(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, NotAuthenticatedRequest {
        return enrollmentRequestService.cancel(publicKey);
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#coursePublicKey, T(com.lms.enums.ECoursePrivilege).REJECT_ENROLLMENT_REQUEST)")
    @PostMapping("/course/{coursePublicKey}/enrollment-request/{enrollmentRequestPublicKey}/reject")
    boolean reject(@PathVariable String coursePublicKey, @PathVariable String enrollmentRequestPublicKey) throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        return enrollmentRequestService.reject(enrollmentRequestPublicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_ENROLLMENT_REQUESTS)")
    @GetMapping("/course/{publicKey}/enrollment-request/counts")
    public Map<String, Integer> getEnrollmentRequestCounts(@PathVariable String publicKey) throws DataNotFoundException {
        return enrollmentRequestService.getRequestCountsOfCourse(publicKey);
    }

}
