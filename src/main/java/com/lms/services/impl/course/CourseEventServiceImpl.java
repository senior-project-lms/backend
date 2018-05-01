package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.Event;
import com.lms.pojos.course.CourseEventPojo;
import com.lms.repositories.CourseEventRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseEventService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseEventServiceImpl implements CourseEventService {

    @Autowired
    private CourseEventRepository courseEventRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private CourseService courseService;


    @Override
    public Event pojoToEntity(CourseEventPojo pojo) {
        Event entity = new Event();
        entity.setTitle(pojo.getTitle());
        entity.setStart(pojo.getStart());
        entity.setEnd(pojo.getEnd());

        return entity;
    }

    @Override
    public CourseEventPojo entityToPojo(Event entity) {
        CourseEventPojo pojo = new CourseEventPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setTitle(entity.getTitle());
        pojo.setStart(entity.getStart());
        pojo.setEnd(entity.getEnd());
        return pojo;
    }

    @Override
    public boolean save(String coursePublicKey, CourseEventPojo pojo) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailsService.getAuthenticatedUser();

        Course course = courseService.findByPublicKey(coursePublicKey);

        Event entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCourse(course);
        entity.setCreatedBy(authUser);

        entity = courseEventRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a course event is saved");
        }

        return true;
    }

    @Override
    public boolean delete(String eventPublicKey) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailsService.getAuthenticatedUser();
        Event entity = courseEventRepository.findByPublicKeyAndVisible(eventPublicKey, true);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a course event is found by publicKey: %s", eventPublicKey));
        }
        entity.setVisible(false);
        entity.setUpdatedBy(authUser);

        entity = courseEventRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a course event is deleted");
        }

        return true;
    }

    @Override
    public boolean update(String coursePublicKey, CourseEventPojo pojo) {
        return false;
    }

    @Override
    public List<CourseEventPojo> getAllEventsOfCourse(String coursePublicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(coursePublicKey);
        List<Event> entities = courseEventRepository.findAllByCourseAndVisible(course, true);

        if (entities == null) {
            return new ArrayList<>();
        }


        List<CourseEventPojo> pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());


        return pojos;
    }


    @Override
    public List<CourseEventPojo> getAllEventsOfRegisteredCoursesOfAuthUser() {

        List<Course> courses = courseService.findAllCoursesOfAutUser();
        List<Event> events = courseEventRepository.findAllByCourseInAndVisible(courses, true);

        List<CourseEventPojo> pojos = events
                .stream()
                .map(entity -> {
                    CourseEventPojo pojo = entityToPojo(entity);
                    pojo.setCourse(courseService.entityToPojo(entity.getCourse()));
                    return pojo;
                })
                .collect(Collectors.toList());


        return pojos;
    }
}
