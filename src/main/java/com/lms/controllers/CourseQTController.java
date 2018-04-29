package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.course.*;
import com.lms.pojos.SuccessPojo;
import com.lms.services.interfaces.course.CourseQTQuestionService;
import com.lms.services.interfaces.course.CourseQTService;
import com.lms.services.interfaces.course.CourseQTUserAnswerService;
import com.lms.services.interfaces.course.CourseQTUserService;
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

    @Autowired
    private CourseQTUserService qtUserService;

    @Autowired
    private CourseQTUserAnswerService qtUserAnswerService;



    @PostMapping("/course/{coursePublicKey}/quiz-test")
    public SuccessPojo save(@PathVariable String coursePublicKey, @RequestBody CourseQuizTestPojo pojo) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {

        if (pojo.getName() == null || pojo.getName().isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }

        return quizTestingService.save(coursePublicKey, pojo);
    }

    @PutMapping("/course/{coursePublicKey}/quiz-test/{publicKey}")
    public SuccessPojo update(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody CourseQuizTestPojo pojo) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {

        if (pojo.getName() == null || pojo.getName().isEmpty()) {
            throw new EmptyFieldException("Name field cannot be empty");
        }

        return quizTestingService.update(publicKey, pojo);
    }


    @DeleteMapping("/course/{coursePublicKey}/quiz-test/{publicKey}")
    public SuccessPojo delete(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {
        return quizTestingService.delete(publicKey);
    }


    @PutMapping("/course/{coursePublicKey}/quiz-test/{publicKey}/publish")
    public SuccessPojo publish(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {
        return quizTestingService.publish(publicKey);
    }

    @PutMapping("/course/{coursePublicKey}/quiz-test/{publicKey}/disable")
    public SuccessPojo disable(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {
        return quizTestingService.disable(publicKey);
    }

    @GetMapping("/course/{coursePublicKey}/quiz-test/{publicKey}")
    public CourseQuizTestPojo get(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws DataNotFoundException {
        return quizTestingService.get(publicKey);
    }


    @GetMapping("/course/{coursePublicKey}/quiz-test/{publicKey}/exam")
    public CourseQuizTestPojo getForExam(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws DataNotFoundException {
        return quizTestingService.getForExam(publicKey);
    }


    @GetMapping("/course/{coursePublicKey}/quiz-test/{publicKey}/before-exam")
    public CourseQuizTestPojo getBeforeExam(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws DataNotFoundException {
        return quizTestingService.getBeforeStartTheExam(publicKey);
    }



    @GetMapping("/course/{coursePublicKey}/quiz-test")
    public List<CourseQuizTestPojo> getAll(@PathVariable String coursePublicKey) throws DataNotFoundException {
        return quizTestingService.getAll(coursePublicKey);
    }


    @GetMapping("/course/{coursePublicKey}/quiz-test/{qtPublicKey}/question/{publicKey}")
    public CourseQTQuestionPojo getQuestion(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @PathVariable String publicKey) throws DataNotFoundException {
        return questionService.get(publicKey);
    }


    @PostMapping("/course/{coursePublicKey}/quiz-test/{qtPublicKey}/question")
    public SuccessPojo saveQuestion(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @RequestBody CourseQTQuestionPojo pojo) throws DataNotFoundException, ExecutionFailException {
        return questionService.save(qtPublicKey, pojo);
    }


    @DeleteMapping("/course/{coursePublicKey}/quiz-test/{qtPublicKey}/question/{questionPublicKey}")
    public SuccessPojo deleteQuestion(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @PathVariable String questionPublicKey) throws DataNotFoundException {
        return questionService.delete(questionPublicKey);
    }


    @PostMapping(value = {"/course/{coursePublicKey}/quiz-test/{qtPublicKey}/start"})
    public SuccessPojo startQT(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey) throws DataNotFoundException, ExecutionFailException {

        return qtUserService.start(coursePublicKey, qtPublicKey);
    }


    @PostMapping(value = {"/course/{coursePublicKey}/quiz-test/{qtPublicKey}/finish"})
    public SuccessPojo finishQT(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey) throws DataNotFoundException, ExecutionFailException {
        return qtUserService.finish(coursePublicKey, qtPublicKey);
    }


    @PostMapping(value = {"/course/{coursePublicKey}/quiz-test/{qtPublicKey}/set-answer"})
    public SuccessPojo setAnswer(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey, @RequestBody CourseQTUserAnswerPojo pojo) throws DataNotFoundException, ExecutionFailException {
        return qtUserAnswerService.setAnswer(qtPublicKey, pojo);
    }


    @GetMapping(value = {"/course/{coursePublicKey}/quiz-test/{qtPublicKey}/qt-user"})
    public CourseQTUserPojo getCourseQTUser(@PathVariable String coursePublicKey, @PathVariable String qtPublicKey) throws DataNotFoundException {
        return qtUserService.get(qtPublicKey);
    }



}
