package com.lms.controllers;

import com.lms.components.ExceptionConverter;
import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.customExceptions.ServiceException;

import com.lms.pojos.course.CoursePojo;
import com.lms.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExceptionConverter exceptionConverter;


    @GetMapping(value = {"/admin/courses/active"})
    public List<CoursePojo> getAllCourses() throws ExecutionFailException, DataNotFoundException {

        try {
            List<CoursePojo> pojos = courseService.getAllByVisible(true);
            return pojos;
        } catch (ServiceException e) {
            exceptionConverter.convert(e);
        }

        throw new DataNotFoundException("No such a course collection found.");
    }

    @PostMapping(value = {"/admin/course"})
    public boolean saveCourse(@RequestBody CoursePojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if (isValidPojo(pojo)) {
            try {
                return courseService.save(pojo);

            } catch (ServiceException e) {
                exceptionConverter.convert(e);
            }
        }
        throw new ExecutionFailException("No such course is saved");
    }

    @PostMapping(value = {"/admin/courses"})
    public boolean saveCourses(@RequestBody List<CoursePojo> pojos) throws ExecutionFailException, EmptyFieldException, DataNotFoundException {

        try {
            for (CoursePojo pojo : pojos) {
                if (!isValidPojo(pojo)) {
                    throw new ExecutionFailException("No such user is saved");

                }
            }
            return courseService.save(pojos);
        } catch (ServiceException e) {
            exceptionConverter.convert(e);

        }


        throw new ExecutionFailException("No such course collection is saved");
    }


    private boolean isValidPojo(CoursePojo pojo) throws EmptyFieldException {
        if (pojo == null) {
            throw new EmptyFieldException("Object cannot be null.");
        } else if (pojo.getCode() == null || pojo.getCode().isEmpty()) {
            throw new EmptyFieldException("Course name field cannot be empty.");
        } else if (pojo.getName() == null || pojo.getName().isEmpty()) {
            throw new EmptyFieldException("Course name field cannot be empty.");
        } else if (pojo.getOwner() == null || pojo.getOwner().getEmail().isEmpty()) {
            throw new EmptyFieldException("Course owner email field cannot be empty.");

        }
        return true;
    }

}
