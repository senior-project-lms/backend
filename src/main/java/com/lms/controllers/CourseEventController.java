package com.lms.controllers;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.course.EventPojo;
import com.lms.services.interfaces.course.CourseEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class CourseEventController {

    @Autowired
    private CourseEventService courseEventService;

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#coursePublicKey, T(com.lms.enums.ECoursePrivilege).READ_COURSE_CALENDAR)")
    @GetMapping("/course/{coursePublicKey}/calendar")
    public List<EventPojo> getAllEventsOfCourse(@PathVariable String coursePublicKey) throws DataNotFoundException {
        return courseEventService.getAllEventsOfCourse(coursePublicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#coursePublicKey, T(com.lms.enums.ECoursePrivilege).DELETE_COURSE_CALENDAR)")
    @DeleteMapping("/course/{coursePublicKey}/calendar/{eventPublicKey}")
    public boolean deleteCourseEvent(@PathVariable String coursePublicKey, @PathVariable String eventPublicKey) throws DataNotFoundException, ExecutionFailException {
        return courseEventService.delete(eventPublicKey);
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#coursePublicKey, T(com.lms.enums.ECoursePrivilege).SAVE_COURSE_CALENDAR)")
    @PostMapping("/course/{coursePublicKey}/calendar")
    public boolean saveCourseEvent(@PathVariable String coursePublicKey, @RequestBody EventPojo eventPojo) throws DataNotFoundException, ExecutionFailException {
        return courseEventService.save(coursePublicKey, eventPojo);
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_GLOBAL_CALENDAR.CODE)")
    @GetMapping("/course/all/calendar")
    public List<EventPojo> getAllEventsOfRegisteredCourses() {
        return courseEventService.getAllEventsOfRegisteredCoursesOfAuthUser();
    }

}
