package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.GlobalQaQuestionPojo;
import com.lms.services.interfaces.GlobalQaQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class GlobalQaQuestionController {

    @Autowired
    private GlobalQaQuestionService globalQaQuestionService;


    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * Globalqaquestion is saved
     *
     * @param pojo
     * @return
     * @author emsal.aynaci
     */
    @PostMapping(value = "/global-qa-question")
    public boolean save(@RequestBody GlobalQaQuestionPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if (pojo == null) {
            throw new EmptyFieldException("");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return globalQaQuestionService.save(pojo);
        }
    }



    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * Update globalQaQuestion
     *
     * @param pojo
     * @return
     * @author emsal aynaci
     */
    @PutMapping(value = {"/global-qa-question"})
    public boolean update(@RequestBody GlobalQaQuestionPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if(pojo == null){
            throw new EmptyFieldException("");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return globalQaQuestionService.update(pojo);
        }
    }


    /*
     * delete global question with publickey the parameters that is null or not, if there is any null returns error else
     * @param publicKey
     * @return
     * @author emsal.aynaci
     */

    @DeleteMapping(value = {"/qlobal-qa-question/{publicKey}"})
    public boolean delete(@PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (publicKey == null || publicKey.isEmpty()) {
            throw new EmptyFieldException("publicKey field cannot be empty");
        }

        return globalQaQuestionService.delete(publicKey);

    }



    /**
     * Gets a path parameter, which is the page number in database,
     * return 10 of Globalqaquestion, for each page, for each request
     *
     * @param page
     * @return List<GlobalQuestionPojo>
     * @author emsal.aynaci
     */

    @GetMapping({"/qlobal-qa-question/{page}"})
    public List<GlobalQaQuestionPojo> getQuestions(@PathVariable int page) throws EmptyFieldException, ExecutionFailException, DataNotFoundException{

        if (page < 0){
            throw new EmptyFieldException("Page number cannot be negative");
        }

        return globalQaQuestionService.getAllByPage(page);

    }
}





//who published