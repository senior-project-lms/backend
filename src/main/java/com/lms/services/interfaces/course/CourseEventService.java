package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Event;
import com.lms.pojos.course.CourseEventPojo;

import java.util.List;

public interface CourseEventService {

    Event pojoToEntity(CourseEventPojo pojo);

    CourseEventPojo entityToPojo(Event event);

    boolean save(String coursePublicKey, CourseEventPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean delete(String eventPublicKey) throws DataNotFoundException, ExecutionFailException;

    boolean update(String coursePublicKey, CourseEventPojo pojo);

    List<CourseEventPojo> getAllEventsOfCourse(String coursePublicKey) throws DataNotFoundException;

    List<CourseEventPojo> getAllEventsOfRegisteredCoursesOfAuthUser();
}
