package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.enums.VoteType;
import com.lms.pojos.course.CourseQAPojo;
import com.lms.pojos.course.CourseQACommentPojo;
import com.lms.pojos.course.CourseQATagPojo;
import com.lms.services.interfaces.course.CourseQACommentService;
import com.lms.services.interfaces.course.CourseQAService;
import com.lms.services.interfaces.course.CourseQATagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseQAController {

    @Autowired
    private CourseQAService courseQAService;

    @Autowired
    private CourseQACommentService courseQACommentService;

    @Autowired
    private CourseQATagService courseQATagService;

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * qaquestion is saved
     *
     * @param pojo
     * @return
     * @author emsal.aynaci
     */

    @PostMapping(value = "/course/{coursePublicKey}/qa")
    public boolean save(@PathVariable String coursePublicKey, @RequestBody CourseQAPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (!pojo.isAnswer() && (pojo.getTitle() == null || pojo.getTitle().isEmpty())) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return courseQAService.save(coursePublicKey, pojo);
        }

    }

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * Update CourseQA
     *
     * @param pojo
     * @return
     * @author emsal aynaci
     */

    @PutMapping(value = {"/course/{coursePublicKey}/qa"})
    public boolean update(@PathVariable String coursePublicKey, @RequestBody CourseQAPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (pojo.getPublicKey() == null || !pojo.getPublicKey().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return courseQAService.update(coursePublicKey, pojo);
        }
    }


    /*
     * delete Qaquestion with publickey the parameters that is null or not, if there is any null returns error else
     * @param publicKey
     * @return
     * @author emsal.aynaci
     */

    @DeleteMapping(value = {"/course/{coursePublicKey}/qa/{publicKey}"})
    public boolean delete(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (publicKey == null || publicKey.isEmpty()) {
            throw new EmptyFieldException("publicKey field cannot be empty");
        }

        return courseQAService.delete(coursePublicKey, publicKey);

    }


    /**
     * Gets a path parameter, which is the page number in database,
     * return 10 of Qaqaquestion, for each page, for each request
     *
     * @param page
     * @return List<CourseQAPojo>
     * @author emsal.aynaci
     */

    @GetMapping({"/course/{coursePublicKey}/qas/{page}"})
    public List<CourseQAPojo> getAll(@PathVariable String coursePublicKey, @PathVariable int page) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (page < 1) {
            throw new EmptyFieldException("Page number cannot be negative");
        }

        return courseQAService.getAll(coursePublicKey, --page);

    }

    @GetMapping({"/course/{coursePublicKey}/qa/{publicKey}"})
    public CourseQAPojo get(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws DataNotFoundException {

        return courseQAService.getByPublicKey(publicKey);

    }


    @PostMapping(value = "/course/{coursePublicKey}/qa/{publicKey}/comment")
    public boolean saveComment(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody CourseQACommentPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return courseQACommentService.save(publicKey, pojo);
        }
    }

    @PostMapping(value = {"/course/{coursePublicKey}/qa/{publicKey}/up-vote"})
    public boolean upVote(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseQAService.vote(publicKey, VoteType.UP);
    }

    @PostMapping(value = {"/course/{coursePublicKey}/qa/{publicKey}/down-vote"})
    public boolean downVote(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseQAService.vote(publicKey, VoteType.DOWN);
    }


    @PostMapping(value = {"/course/{coursePublicKey}/qa/{publicKey}/star-vote"})
    public boolean starVote(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseQAService.vote(publicKey, VoteType.STAR);
    }

    @GetMapping(value = {"/course/{coursePublicKey}/qa/{publicKey}/relateds"})
    public List<CourseQAPojo> getRelateds(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws DataNotFoundException {
        return courseQAService.getTop10RelatedTopics(publicKey);
    }

    @GetMapping(value = {"/course/{coursePublicKey}/qa/tag/{name}"})
    public List<CourseQATagPojo> searchByTag(@PathVariable String coursePublicKey, @PathVariable String name) {
        return courseQATagService.searchByName(name);
    }

}
