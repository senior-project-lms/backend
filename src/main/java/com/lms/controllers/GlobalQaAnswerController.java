package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.pojos.GlobalQaAnswerPojo;
import com.lms.services.interfaces.GlobalQaAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class GlobalQaAnswerController{

    @Autowired
    GlobalQaAnswerService globalQaAnswerService;

    @GetMapping(value = {"/answers"})
    public List<GlobalQaAnswerPojo> getAnswers(@PathVariable String questionPublicKey ) throws DataNotFoundException {

        return globalQaAnswerService.getQuestionAnswersByQuestionPublicKey(questionPublicKey);
    }


}
