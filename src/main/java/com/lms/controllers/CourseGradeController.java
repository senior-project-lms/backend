package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.GradePojo;
import com.lms.pojos.course.UserScorePojo;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseGradeController {

    @Autowired
    private CourseGradeService courseGradeService;

    @Autowired
    private CourseScoreService courseScoreService;


    @PostMapping(value = {"/course/{coursePublicKey}/grade"})
    public SuccessPojo save(@PathVariable String coursePublicKey, @RequestBody GradePojo pojo) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.save(coursePublicKey, pojo);
    }

    @PutMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}"})
    public SuccessPojo update(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody GradePojo pojo) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.update(publicKey, pojo);
    }

    @DeleteMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}"})
    public SuccessPojo delete(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.delete(publicKey);
    }

    @GetMapping(value = {"/course/{coursePublicKey}/grades"})
    public List<GradePojo> getAll(@PathVariable String coursePublicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.getAll(coursePublicKey);
    }

    @GetMapping(value = {"/course/{coursePublicKey}/student-grades"})
    public List<GradePojo> getAllForStudents(@PathVariable String coursePublicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.getAllForAuthStudent(coursePublicKey);
    }


    @GetMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}"})
    public GradePojo get(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.get(publicKey);
    }

    @GetMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}/view"})
    public GradePojo getForView(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.getForView(publicKey);
    }

    @PutMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}/publish"})
    public boolean publish(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.publish(publicKey, true);
    }

    @PutMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}/disable"})
    public boolean disable(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException {
        return courseGradeService.publish(publicKey, false);
    }

    @PostMapping(value = {"/course/{coursePublicKey}/grade/{publicKey}/score"})
    public SuccessPojo saveScore(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody UserScorePojo pojo) throws ExecutionFailException, DataNotFoundException {
        return courseScoreService.save(publicKey, pojo);
    }


    @PostMapping(value = {"/course/{coursePublicKey}/grade/save-all"})
    public boolean saveAll(@PathVariable String coursePublicKey,@RequestBody List<GradePojo> pojos) throws ExecutionFailException, DataNotFoundException {

        for(GradePojo grade : pojos){
            SuccessPojo success = courseGradeService.save(coursePublicKey, grade);
            if (grade.getUserScores() != null){

                for (UserScorePojo score : grade.getUserScores()){
                    courseScoreService.save(success.getPublicKey(), score);
                }
            }

        }
        return true;


    }

}
