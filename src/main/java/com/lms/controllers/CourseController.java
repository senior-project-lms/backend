package com.lms.controllers;

import com.lms.customExceptions.*;

import com.lms.pojos.UserPojo;
import com.lms.pojos.course.CoursePojo;
import com.lms.pojos.course.UserCoursePrivilegePojo;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api"})
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseAssignmentService courseAssignmentService;


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_COURSES_BY_VISIBILITY.CODE)")
    @GetMapping(value = {"/courses/active"})
    public List<CoursePojo> getAllCourses() throws DataNotFoundException {

        return courseService.getAllByVisible(true);
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_COURSES_BY_VISIBILITY.CODE)")
    @GetMapping(value = {"/courses/deactivated"})
    public List<CoursePojo> getAllDeactivatedCourses() throws DataNotFoundException {
        return courseService.getAllByVisible(false);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).SAVE_COURSE.CODE)")
    @PostMapping(value = {"/course"})
    public boolean saveCourse(@RequestBody CoursePojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException, ExistRecordException {
        if (isValidPojo(pojo)) {
            return courseService.save(pojo);
        }
        return false;
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).SAVE_COURSE.CODE)")
    @PostMapping(value = {"/courses"})
    public boolean saveCourses(@RequestBody List<CoursePojo> pojos) throws ExecutionFailException, EmptyFieldException, DataNotFoundException, ExistRecordException {

        for (CoursePojo pojo : pojos) {
            if (!isValidPojo(pojo)) {
                throw new ExecutionFailException("No such user is saved");
            }
        }
        return courseService.save(pojos);

    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_COURSE_STATUSES.CODE)")
    @GetMapping("/courses/statuses")
    public Map<String, Integer> getCoursesStatuses() {
        return courseService.getCourseStatus();

    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).UPDATE_COURSE_VISIBILITY.CODE)")
    @PutMapping("/course/{publicKey}/visible")
    public boolean setVisible(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, EmptyFieldException {
        if (publicKey != null || !publicKey.isEmpty()) {
            return courseService.updateVisibility(publicKey, true);

        }
        throw new EmptyFieldException("PublicKey is empty");
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).UPDATE_COURSE_VISIBILITY.CODE)")
    @PutMapping("/course/{publicKey}/invisible")
    public boolean setInvisible(@PathVariable String publicKey) throws ExecutionFailException, DataNotFoundException, EmptyFieldException {
        if (publicKey != null || !publicKey.isEmpty()) {
            return courseService.updateVisibility(publicKey, false);
        }
        throw new EmptyFieldException("PublicKey is empty");

    }

    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).READ_NOT_REGISTERED_COURSES.CODE)")
    @GetMapping("/courses/not-registered/code/{param}")
    public List<CoursePojo> getNotRegisteredCoursesByCode(@PathVariable String param) throws DataNotFoundException {

        return courseService.getNotRegisteredCoursesByCodeByAuthUser(param);

    }


    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).READ_NOT_REGISTERED_COURSES.CODE)")
    @GetMapping("/courses/not-registered/name/{param}")
    public List<CoursePojo> getNotRegisteredCoursesByName(@PathVariable String param) throws DataNotFoundException {

        return courseService.getNotRegisteredCoursesByNameByAuthUser(param);

    }


    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).READ_NOT_REGISTERED_COURSES.CODE)")
    @PostMapping("/courses/not-registered/lecturer")
    public List<CoursePojo> getNotRegisteredCoursesByLecturer(@RequestBody UserPojo pojo) throws DataNotFoundException, EmptyFieldException {


        if ((pojo.getName() == null || pojo.getName().isEmpty()) && (pojo.getSurname() == null || pojo.getSurname().isEmpty())) {
            throw new EmptyFieldException("name and surname cannot be empty");

        }
        return courseService.getNotRegisteredCoursesByLecturerByAuthUser(pojo.getName(), pojo.getSurname());

    }


    @PreAuthorize("hasRole(T(com.lms.enums.ECoursePrivilege).READ_REGISTERED_COURSES.CODE) || hasRole(T(com.lms.enums.ECoursePrivilege).READ_AUTHENTICATED_COURSES.CODE)")
    @GetMapping(value = {"/me/courses"})
    public List<CoursePojo> getAuthUserCourses() throws DataNotFoundException {
        return courseService.getAuthUserCourses();
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_REGISTERED_STUDENTS)")
    @GetMapping(value = {"/course/{publicKey}/enrolled-users"})
    public List<UserPojo> getEnrolledUsers(@PathVariable String publicKey) throws DataNotFoundException {
        return courseService.getEnrolledUsers(publicKey);
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_REGISTERED_STUDENTS)")
    @GetMapping(value = {"/course/{publicKey}/observer-users"})
    public List<UserPojo> getEnrolledObserverUsers(@PathVariable String publicKey) throws DataNotFoundException {
        return courseService.getEnrolledObserverUsers(publicKey);
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).READ_REGISTERED_STUDENTS)")
    @GetMapping(value = {"/course/{publicKey}/all-registered-users"})
    public List<UserPojo> getAllRegisteredUsers(@PathVariable String publicKey) throws DataNotFoundException {
        return courseService.getAllRegisteredUsers(publicKey);
    }


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).GLOBAL_ACCESS.CODE)")
    @GetMapping(value = {"/course/{publicKey}/info"})
    public CoursePojo getBasicCourseInfo(@PathVariable String publicKey) throws DataNotFoundException {
        return courseService.getBasicCourseInfo(publicKey);
    }


    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#publicKey, T(com.lms.enums.ECoursePrivilege).SAVE_AUTHENTICATED_USER)")
    @PostMapping(value = {"/course/{publicKey}/assistant"})
    public boolean saveCourseAssistant(@PathVariable String publicKey, @RequestBody UserCoursePrivilegePojo pojo) throws DataNotFoundException, ExecutionFailException {
        return courseService.registerUserAsAssistantToCourse(publicKey, pojo);
    }

    @PreAuthorize("@methodSecurity.hasCoursePrivilege(#coursePublicKey, T(com.lms.enums.ECoursePrivilege).DELETE_AUTHENTICATED_USER)")
    @DeleteMapping(value = {"/course/{coursePublicKey}/assistant/{userPublicKey}"})
    public boolean deleteCourseAssistant(@PathVariable String coursePublicKey, @PathVariable String userPublicKey) throws DataNotFoundException, ExecutionFailException {
        return courseService.deleteAssistantUser(coursePublicKey, userPublicKey);
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

    @GetMapping({"/course/{publicKey}/assignment/counts"})
    public Map<String,Integer> getAssignmentsCounts(@PathVariable String publicKey) throws DataNotFoundException{
        Map<String,Integer> response = new HashMap<>();
        response.put("assignment",courseAssignmentService.getPendingCountsOfAssignments(publicKey));
        return response;
    }


}