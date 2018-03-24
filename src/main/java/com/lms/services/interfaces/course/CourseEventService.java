package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Event;
import com.lms.pojos.course.EventPojo;

import java.util.List;

public interface CourseEventService {

    Event pojoToEntity(EventPojo pojo);

    EventPojo entityToPojo(Event event);

    boolean save(String coursePublicKey, EventPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean delete(String eventPublicKey) throws DataNotFoundException, ExecutionFailException;

    boolean update(String coursePublicKey, EventPojo pojo);

    List<EventPojo> getAllEventsOfCourse(String coursePublicKey) throws DataNotFoundException;

    List<EventPojo> getAllEventsOfRegisteredCoursesOfAuthUser();
}
