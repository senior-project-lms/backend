package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.enums.VoteType;
import com.lms.pojos.course.QACommentPojo;
import com.lms.pojos.course.QAPojo;
import com.lms.services.interfaces.course.CourseQACommentService;
import com.lms.services.interfaces.course.CourseQAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class QAController {

    @Autowired
    private CourseQAService courseQAService;

    @Autowired
    private CourseQACommentService courseQACommentService;

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * qaquestion is saved
     *
     * @param pojo
     * @return
     * @author emsal.aynaci
     */

    @PostMapping(value = "/course/{publicKey}/qa-question")
    public boolean save(@PathVariable String publicKey, @RequestBody QAPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if(pojo == null) {
            throw new EmptyFieldException("");
        } else if (pojo.getCourse() == null ){
            throw new EmptyFieldException("Course cannot be empty");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return courseQAService.save(pojo);
        }

    }

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * Update QA
     *
     * @param pojo
     * @return
     * @author emsal aynaci
     */

    @PutMapping(value = {"/course/{coursePublicKey}/qa-question"})
    public boolean update(@PathVariable String coursePublicKey, @RequestBody QAPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if(pojo == null){
            throw new EmptyFieldException("");
        }else if (pojo.getCourse() == null ) {
            throw new EmptyFieldException("Course cannot be empty");
        }else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return courseQAService.update(pojo);
        }
    }


    /*
     * delete Qaquestion with publickey the parameters that is null or not, if there is any null returns error else
     * @param publicKey
     * @return
     * @author emsal.aynaci
     */

    @DeleteMapping(value = {"/course/{coursePublicKey}/qa-question/{publicKey}"})
    public boolean delete(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (publicKey == null || publicKey.isEmpty()) {
            throw new EmptyFieldException("publicKey field cannot be empty");
        }

        return courseQAService.delete(publicKey);

    }


    /**
     * Gets a path parameter, which is the page number in database,
     * return 10 of Qaqaquestion, for each page, for each request
     *
     * @param page
     * @return List<QAPojo>
     * @author emsal.aynaci
     */

    @GetMapping({"/course/{coursePublicKey}/qa-question/{page}"})
    public List<QAPojo> getAll(@PathVariable String coursePublicKey, @PathVariable int page) throws EmptyFieldException, ExecutionFailException, DataNotFoundException{

        if (page < 0){
            throw new EmptyFieldException("Page number cannot be negative");
        }

        return courseQAService.getAll(coursePublicKey, page);

    }

    @GetMapping({"/course/{coursePublicKey}/qa-question/{publicKey}"})
    public QAPojo get(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws DataNotFoundException{

        return courseQAService.getByPublicKey(publicKey);

    }


    @PostMapping(value = "/course/{coursePublicKey}/qa-question/{publicKey}/comment")
    public boolean saveComment(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody QACommentPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return courseQACommentService.save(publicKey, pojo);
        }
    }

    @PostMapping(value = {"/course/{coursePublicKey}/qa-question/{publicKey}/up-vote"})
    public boolean upVote(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseQAService.vote(publicKey, VoteType.UP);
    }

    @PostMapping(value = {"/course/{coursePublicKey}/qa-question/{publicKey}/down-vote"})
    public boolean downVote(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseQAService.vote(publicKey, VoteType.DOWN);

    }


    @PostMapping(value = {"/course/{coursePublicKey}/qa-question/{publicKey}/star-vote"})
    public boolean starVote(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseQAService.vote(publicKey, VoteType.STAR);

    }
}
