package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.course.CourseAnnouncementPojo;
import com.lms.services.interfaces.course.CourseAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseAnnouncementController {

    @Autowired
    private CourseAnnouncementService courseAnnouncementService;

    @PostMapping(value = {"/courses/{coursePublicKey}/announcements"})
    public boolean save(@PathVariable String coursePublicKey, @RequestBody CourseAnnouncementPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {

        if (pojo == null) {
            throw new EmptyFieldException("Course Announcement object cannot be empty");
        } else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()) {
            throw new EmptyFieldException("Title field cannot be empty");
        } else if (pojo.getContent() == null || pojo.getContent().isEmpty()) {
            throw new EmptyFieldException(" Content field cannot be empty");
        } else {
            return courseAnnouncementService.save(coursePublicKey, pojo);
        }
    }

    @DeleteMapping(value = {"/courses/{coursePublicKey}/announcements"})
    public boolean delete(@PathVariable String coursePublicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if (coursePublicKey == null || coursePublicKey.isEmpty()) {
            throw new EmptyFieldException("field cannot be empty");
        }
        return courseAnnouncementService.delete(coursePublicKey);

    }

    @GetMapping({"/courses/{coursePublicKey}/announcements/{page}"})
    public List<CourseAnnouncementPojo> getAnnouncements(@PathVariable String coursePublicKey, @PathVariable int page) throws EmptyFieldException, DataNotFoundException {
        if (page < 1) {
            throw new EmptyFieldException("Page number cannot be negative");
        }
        return courseAnnouncementService.getAllByPage(coursePublicKey, --page);
    }

}
