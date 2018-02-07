package com.lms.controllers;

import com.lms.components.ExceptionConverter;
import com.lms.customExceptions.*;

import com.lms.pojos.course.CoursePojo;
import com.lms.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExceptionConverter exceptionConverter;


    @GetMapping(value = {"/courses/active"})
    public List<CoursePojo> getAllCourses() throws ExecutionFailException, DataNotFoundException, ExistRecordException {

        try {
            List<CoursePojo> pojos = courseService.getAllByVisible(true);
            return pojos;
        } catch (ServiceException e) {
            exceptionConverter.convert(e);
        }

        throw new DataNotFoundException("No such a course collection found.");
    }

    @GetMapping(value = {"/courses/deactivated"})
    public List<CoursePojo> getAllDeactivatedCourses() throws ExecutionFailException, DataNotFoundException, ExistRecordException {

        try {
            List<CoursePojo> pojos = courseService.getAllByVisible(false);
            return pojos;
        } catch (ServiceException e) {
            exceptionConverter.convert(e);
        }

        throw new DataNotFoundException("No such a course collection found.");
    }



    @PostMapping(value = {"/admin/course"})
    public boolean saveCourse(@RequestBody CoursePojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException, ExistRecordException {
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
    public boolean saveCourses(@RequestBody List<CoursePojo> pojos) throws ExecutionFailException, EmptyFieldException, DataNotFoundException, ExistRecordException {

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


    @GetMapping("/admin/courses/statuses")
    public Map<String, Integer> getCoursesStatueses() throws ExecutionFailException, DataNotFoundException, ExistRecordException {
        try {
            return courseService.getCourseStatus();
        } catch (ServiceException e) {
            exceptionConverter.convert(e);
        }

        throw new ExecutionFailException("No such course status is selected");

    }


    private boolean isValidPojo(CoursePojo pojo) throws EmptyFieldException, ExistRecordException {

        if (pojo == null) {
            throw new EmptyFieldException("Object cannot be null.");
        } else if (pojo.getCode() == null || pojo.getCode().isEmpty()) {
            throw new EmptyFieldException("Course name field cannot be empty.");
        } else if (pojo.getName() == null || pojo.getName().isEmpty()) {
            throw new EmptyFieldException("Course name field cannot be empty.");
        } else if (pojo.getOwner() == null || pojo.getOwner().getEmail().isEmpty()) {
            throw new EmptyFieldException("Course owner email field cannot be empty.");
        } else if (courseService.codeAlreadyExist(pojo.getCode())) {
            throw new ExistRecordException(String.format("%s course code is already exist", pojo.getCode()));
        }
        return true;
    }

}
