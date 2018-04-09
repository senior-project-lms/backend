package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQA;
import com.lms.entities.GlobalQAComment;
import com.lms.enums.VoteType;
import com.lms.pojos.GlobalQACommentPojo;
import com.lms.pojos.GlobalQAPojo;
import com.lms.services.interfaces.GlobalQACommentService;
import com.lms.services.interfaces.GlobalQAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class GlobalQAController {

    @Autowired
    private GlobalQAService globalQAService;

    @Autowired
    private GlobalQACommentService globalQACommentService;

    /**
     * Checks pojo parameter that will be saved, is null or not, if there is any null returns error else
     * Globalqaquestion is saved
     *
     * @param pojo
     * @return
     * @author emsal.aynaci
     */
    @PostMapping(value = "/global-qa")
    public boolean save(@RequestBody GlobalQAPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (!pojo.isAnswer() && (pojo.getTitle() == null || pojo.getTitle().isEmpty())) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return globalQAService.save(pojo);
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
    @PutMapping(value = {"/global-qa/{publicKey}"})
    public boolean update(@PathVariable String publicKey, @RequestBody GlobalQAPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (!pojo.isAnswer() && (pojo.getTitle() == null || pojo.getTitle().isEmpty())) {

            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return globalQAService.update(publicKey, pojo);
        }
    }


    /*
     * delete global question with publickey the parameters that is null or not, if there is any null returns error else
     * @param publicKey
     * @return
     * @author emsal.aynaci
     */

    @DeleteMapping(value = {"/global-qa/{publicKey}"})
    public boolean delete(@PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (publicKey == null || publicKey.isEmpty()) {
            throw new EmptyFieldException("publicKey field cannot be empty");
        }

        return globalQAService.delete(publicKey);

    }



    /**
     * Gets a path parameter, which is the page number in database,
     * return 10 of Globalqaquestion, for each page, for each request
     *
     * @param page
     * @return List<GlobalQuestionPojo>
     * @author emsal.aynaci
     */

    @GetMapping({"/global-qas/{page}"})
    public List<GlobalQAPojo> getAll(@PathVariable int page) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (page < 1){
            throw new EmptyFieldException("Page number cannot be negative");
        }

        return globalQAService.getAllByPage(--page);

    }

    @GetMapping({"/global-qa/{publicKey}"})
    public GlobalQAPojo get(@PathVariable String publicKey) throws DataNotFoundException{

        return globalQAService.getByPublicKey(publicKey);

    }


    @PostMapping(value = "/global-qa/{publicKey}/comment")
    public boolean saveComment(@PathVariable String publicKey, @RequestBody GlobalQACommentPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

       if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException("Content field cannot be empty");
        } else {
            return globalQACommentService.save(publicKey, pojo);
        }
    }

    @PostMapping(value = {"/global-qa/{publicKey}/up-vote"})
    public boolean upVote(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, SerialException {
        return globalQAService.vote(publicKey, VoteType.UP);
    }

    @PostMapping(value = {"/global-qa/{publicKey}/down-vote"})
    public boolean downVote(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, SerialException {
        return globalQAService.vote(publicKey, VoteType.DOWN);

    }


    @PostMapping(value = {"/global-qa/{publicKey}/star-vote"})
    public boolean starVote(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, SerialException {
        return globalQAService.vote(publicKey, VoteType.STAR);

    }


}





//who published