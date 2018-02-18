package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.pojos.course.EnrolmentRequestPojo;
import com.lms.services.interfaces.EnrolmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"/api"})
public class EnrolmentRequestController {


    @Autowired
    private EnrolmentRequestService enrolmentRequestService;

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).ENROLL_COURSE.CODE)")
    @PostMapping("/course/{publicKey}/enroll")
    public boolean enroll(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return enrolmentRequestService.enroll(publicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.EPrivilege).READ_ENROLMENT_REQUESTS)")
    @GetMapping("/course/{publicKey}/enrolment-requests")
    public List<EnrolmentRequestPojo> getEnrolmentRequests(@PathVariable String publicKey) throws DataNotFoundException {
        return enrolmentRequestService.getEnrolmentRequests(publicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.EPrivilege).APPROVE_ENROLMENT_REQUEST)")
    @GetMapping("/course/{enrolmentRequestPublicKey}/approve")
    public boolean approveEnrollmentRequest(@PathVariable String enrolmentRequestPublicKey) throws ExecutionFailException, DataNotFoundException {
        return enrolmentRequestService.approveEnrolmentRequest(enrolmentRequestPublicKey);
    }


}
