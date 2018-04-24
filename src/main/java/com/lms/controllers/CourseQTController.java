package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQTQuestionPojo;
import com.lms.pojos.course.CourseQuizTestPojo;
import com.lms.services.interfaces.course.CourseQTQuestionService;
import com.lms.services.interfaces.course.CourseQTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseQTController {

    @Autowired
    private CourseQTService quizTestingService;

    @Autowired
    private CourseQTQuestionService questionService;


    @PostMapping("/course/{coursePublicKey}/quiz-test")
    public SuccessPojo save(@PathVariable String coursePublicKey, @RequestBody CourseQuizTestPojo pojo) throws EmptyFieldException, DataNotFoundException {

        if (pojo.getName() == null || pojo.getName().isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }

        return quizTestingService.save(coursePublicKey, pojo);
    }

    @PutMapping("/course/{coursePublicKey}/quiz-test/{publicKey}")
    public SuccessPojo update(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody CourseQuizTestPojo pojo) throws EmptyFieldException, DataNotFoundException {

        if (pojo.getName() == null || pojo.getName().isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }

        return quizTestingService.update(publicKey, pojo);
    }


    @DeleteMapping("/course/{coursePublicKey}/quiz-test/{publicKey}")
    public SuccessPojo delete(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, DataNotFoundException {
        return quizTestingService.delete(publicKey);
    }

    @GetMapping("/course/{coursePublicKey}/quiz-test/{publicKey}")
    public CourseQuizTestPojo get(@PathVariable String coursePublicKey, @PathVariable String publicKey) {
        return quizTestingService.get(publicKey);
    }

    @GetMapping("/course/{coursePublicKey}/quiz-test")
    public List<CourseQuizTestPojo> getAll(@PathVariable String coursePublicKey) throws DataNotFoundException {
        return quizTestingService.getAll(coursePublicKey);
    }


    @GetMapping("/course/{coursePublicKey}/quiz-test/{qtPublicKey}/question/{publicKey}")
    public CourseQTQuestionPojo getQuestion(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @PathVariable String publicKey) {
        return questionService.get(publicKey);
    }


    @PostMapping("/course/{coursePublicKey}/quiz-test/{qtPublicKey}/question")
    public SuccessPojo saveQuestion(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @RequestBody CourseQTQuestionPojo pojo) throws DataNotFoundException {
        return questionService.save(qtPublicKey, pojo);
    }


    @DeleteMapping("/course/{coursePublicKey}/quiz-test/{qtPublicKey}/question/{questionPublicKey}")
    public SuccessPojo deleteQuestion(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @PathVariable String questionPublicKey) throws DataNotFoundException {
        return questionService.delete(questionPublicKey);
    }

}
