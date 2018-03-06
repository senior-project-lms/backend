package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.course.QaQuestionPojo;
import com.lms.services.interfaces.QaQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class QaQuestionController {

    @Autowired
    private QaQuestionService qaQuestionService;

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * qaquestion is saved
     *
     * @param pojo
     * @return
     * @author emsal.aynaci
     */

    @PostMapping(value = "/qa-question")
    public boolean save(@RequestBody QaQuestionPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if(pojo == null) {
            throw new EmptyFieldException("");
        } else if (pojo.getCourse() == null ){
            throw new EmptyFieldException("Course cannot be empty");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return qaQuestionService.save(pojo);
        }

    }

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * Update QaQuestion
     *
     * @param pojo
     * @return
     * @author emsal aynaci
     */

    @PutMapping(value = {"/qa-question"})
    public boolean update(@RequestBody QaQuestionPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if(pojo == null){
            throw new EmptyFieldException("");
        }else if (pojo.getCourse() == null ) {
            throw new EmptyFieldException("Course cannot be empty");
        }else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return qaQuestionService.update(pojo);
        }
    }


    /*
     * delete Qaquestion with publickey the parameters that is null or not, if there is any null returns error else
     * @param publicKey
     * @return
     * @author emsal.aynaci
     */

    @DeleteMapping(value = {"/qa-question/{publicKey}"})
    public boolean delete(@PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (publicKey == null || publicKey.isEmpty()) {
            throw new EmptyFieldException("publicKey field cannot be empty");
        }

        return qaQuestionService.delete(publicKey);

    }


    /**
     * Gets a path parameter, which is the page number in database,
     * return 10 of Qaqaquestion, for each page, for each request
     *
     * @param page
     * @return List<QaQuestionPojo>
     * @author emsal.aynaci
     */

    @GetMapping({"/qa-question/{page}"})
    public List<QaQuestionPojo> getQuestions(@PathVariable int page) throws EmptyFieldException, ExecutionFailException, DataNotFoundException{

        if (page < 0){
            throw new EmptyFieldException("Page number cannot be negative");
        }

        return qaQuestionService.getAllByPage(--page);

    }

}
