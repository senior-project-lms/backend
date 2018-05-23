package com.lms.controllers;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseAssignmentPojo;
import com.lms.pojos.course.StudentAssignmentPojo;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.StudentAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseAssignmentController {

    @Autowired
    private CourseAssignmentService courseAssignmentService;

    @Autowired
    private StudentAssignmentService studentAssignmentService;


    @GetMapping({"/course/{coursePublicKey}/assignments/student"})
    public List<CourseAssignmentPojo> getCourseAssignmentsOfStudent(@PathVariable String coursePublicKey) throws EmptyFieldException, DataNotFoundException {
        return courseAssignmentService.getAllForStudents(coursePublicKey);
    }


    @GetMapping({"/course/{coursePublicKey}/assignments"})
    public List<CourseAssignmentPojo> getCourseAssignments(@PathVariable String coursePublicKey) throws EmptyFieldException, DataNotFoundException {
        return courseAssignmentService.getAll(coursePublicKey);
    }

    @GetMapping({"/course/{coursePublicKey}/assignment/{publicKey}"})
    public CourseAssignmentPojo getCourseAssignment(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, DataNotFoundException {
        return courseAssignmentService.get(publicKey);
    }


    @GetMapping({"/course/{coursePublicKey}/assignment/{publicKey}/student"})
    public CourseAssignmentPojo getCourseAssignmentForStudent(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, DataNotFoundException {
        return courseAssignmentService.getForAuthStudent(publicKey);
    }


    @PostMapping(value = {"/course/{coursePublicKey}/assignment"})
    public boolean save(@PathVariable String coursePublicKey, @RequestBody CourseAssignmentPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (pojo == null) {
            throw new EmptyFieldException("Course Assignment object cannot be empty");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Name cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException(" Content field cannot be empty");
        } else {
            return courseAssignmentService.save(coursePublicKey, pojo);
        }
    }


    @PutMapping(value = {"/course/{coursePublicKey}/assignment/{publicKey}"})
    public boolean update(@PathVariable String coursePublicKey, @PathVariable String publicKey,  @RequestBody CourseAssignmentPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (pojo == null) {
            throw new EmptyFieldException("Course Assignment object cannot be empty");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Name cannot be empty");
        } else {
            return courseAssignmentService.update(publicKey, pojo);
        }
    }

    @DeleteMapping(value = {"/course/{coursePublicKey}/assignment/{publicKey}"})
    public boolean delete(@PathVariable String coursePublicKey, @PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        return courseAssignmentService.delete(publicKey);

    }

    @GetMapping({"/course/{coursePublicKey}/assignment/{publicKey}/student-assignments"})
    public List<StudentAssignmentPojo> getStudentAssignments(@PathVariable String coursePublicKey, @PathVariable String publicKey)throws EmptyFieldException, DataNotFoundException{

        return studentAssignmentService.getAllStudentAssignments(publicKey);
    }

    @GetMapping({"/course/{coursePublicKey}/assignment/{publicKey}/me"})
    public StudentAssignmentPojo getAuthStudentAssignment(@PathVariable String coursePublicKey, @PathVariable String publicKey)throws EmptyFieldException, DataNotFoundException{
        return studentAssignmentService.getAuthStudentAssignment(publicKey);
    }

    @PostMapping({"/course/{coursePublicKey}/assignment/{publicKey}/me"})
    public SuccessPojo saveStudentAssignment(@PathVariable String coursePublicKey, @PathVariable String publicKey, @RequestBody StudentAssignmentPojo pojo) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {
        return studentAssignmentService.save(publicKey, pojo);
    }

    @PutMapping({"/course/{coursePublicKey}/assignment/{publicKey}/me/{stdAssignmentPublicKey}"})
    public SuccessPojo updateStudentAssignment(@PathVariable String coursePublicKey, @PathVariable String publicKey,@PathVariable String stdAssignmentPublicKey, @RequestBody StudentAssignmentPojo pojo) throws EmptyFieldException, DataNotFoundException, ExecutionFailException {
        return studentAssignmentService.update(stdAssignmentPublicKey, pojo);
    }

    @PutMapping({"/course/{coursePublicKey}/assignment/{publicKey}/publish"})
    public boolean publish(@PathVariable String coursePublicKey,  @PathVariable String publicKey) throws DataNotFoundException, ExecutionFailException {

        return courseAssignmentService.publish(publicKey, true);
    }

    @PutMapping({"/course/{coursePublicKey}/assignment/{publicKey}/disable"})
    public boolean disable(@PathVariable String coursePublicKey,  @PathVariable String publicKey) throws DataNotFoundException, ExecutionFailException {

        return courseAssignmentService.publish(publicKey, false);
    }



}