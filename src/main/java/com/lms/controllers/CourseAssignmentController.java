package com.lms.controllers;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
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

    @GetMapping({"/courses/{coursePublicKey}/assignments"})
    public List<CourseAssignmentPojo> getCourseAssignments(@PathVariable String coursePublicKey) throws EmptyFieldException, DataNotFoundException {

        return courseAssignmentService.getAllAssignmentsOfCourse(coursePublicKey);
    }


    @PostMapping(value = {"/courses/{coursePublicKey}/assignments"})
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

    @DeleteMapping(value = {"/courses/{coursePublicKey}/assignments"})
    public boolean delete(@PathVariable String coursePublicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if (coursePublicKey == null || coursePublicKey.isEmpty()) {
            throw new EmptyFieldException("field cannot be empty");
        }
        return courseAssignmentService.delete(coursePublicKey);

    }

    @GetMapping({"/courses/{coursePublicKey}/studentAssignments"})
    public List<StudentAssignmentPojo> getStudentAssignments(@PathVariable String assignmentPublicKey)throws EmptyFieldException, DataNotFoundException{

        return studentAssignmentService.getStudentAssignmentsByAssignmentPublicKey(assignmentPublicKey);
    }



}
