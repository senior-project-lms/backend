package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.pojos.course.QaAnswerPojo;
import com.lms.services.interfaces.QaAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/api")
public class QaAnswerController {

    @Autowired
    QaAnswerService qaAnswerService;

    @GetMapping(value = {"/answers"})
    public List<QaAnswerPojo> getAnswers(@PathVariable String questionPublicKey ) throws DataNotFoundException {

        return qaAnswerService.getQuestionAnswersByQuestionPublicKey(questionPublicKey);
    }
}
