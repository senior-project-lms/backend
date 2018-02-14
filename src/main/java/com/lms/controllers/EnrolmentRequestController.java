package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ExistRecordException;
import com.lms.pojos.course.EnrolmentRequestPojo;
import com.lms.services.interfaces.EnrolmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"/api"})
public class EnrolmentRequestController {


    @Autowired
    private EnrolmentRequestService enrolmentRequestService;


    @PostMapping("/course/{publicKey}/enroll")
    public boolean enroll(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return enrolmentRequestService.enroll(publicKey);
    }


    @GetMapping("/course/{publicKey}/enrolment-requests")
    public List<EnrolmentRequestPojo> getEnrolmentRequests(@PathVariable String publicKey) throws DataNotFoundException {
        return enrolmentRequestService.getEnrolmentRequests(publicKey);
    }


    @GetMapping("/course/{enrolmentRequestPublicKey}/approve")
    public boolean approveEnrollmentRequest(@PathVariable String enrolmentRequestPublicKey) throws ExecutionFailException, DataNotFoundException {
        return enrolmentRequestService.approveEnrolmentRequest(enrolmentRequestPublicKey);
    }


}
