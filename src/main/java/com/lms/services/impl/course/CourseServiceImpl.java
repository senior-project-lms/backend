package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.pojos.course.CoursePojo;
import com.lms.repositories.CourseRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Override
    public CoursePojo entityToPojo(Course entity) {
        // boolean registeredUser, boolean userCoursePrivileges
        CoursePojo pojo = new CoursePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setCode(entity.getCode());
        pojo.setName(entity.getName());
        pojo.setOwner(userService.entityToPojo(entity.getOwner()));


        return pojo;
    }

    @Override
    public Course pojoToEntity(CoursePojo pojo) {
        Course entity = new Course();

        entity.setPublicKey(pojo.getPublicKey());
        entity.setName(pojo.getName());
        entity.setCode(pojo.getCode());
        return entity;
    }

    @Override
    public List<CoursePojo> getAllByVisible(boolean visible) throws DataNotFoundException {
        List<CoursePojo> pojos;

        List<Course> entities = courseRepository.findAllByVisible(visible);

        if (entities == null) {
            throw new DataNotFoundException("No such a course list is fetched");
        }

        pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());


        pojos.stream().forEach(pojo -> pojo.setVisible(visible));





        return pojos;
    }

    @Override
    public CoursePojo getByPublicKey(String publicKey) throws DataNotFoundException {

        Course entity = findByPublicKey(publicKey);

        CoursePojo pojo = entityToPojo(entity);

        return pojo;
    }

    @Override
    public boolean save(CoursePojo pojo) throws DataNotFoundException, ExecutionFailException {

        User owner = userService.findByEmail(pojo.getOwner().getEmail());

        Course entity = pojoToEntity(pojo);

        entity.setOwner(owner);

        entity.setCreatedBy(userDetailsService.getAuthenticatedUser());
        entity.generatePublicKey();
        entity = courseRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a course is saved");
        }

        return true;
    }

    @Override
    public boolean save(List<CoursePojo> pojos) throws DataNotFoundException, ExecutionFailException {

        User createdBy = userDetailsService.getAuthenticatedUser();

        List<Course> entities = new ArrayList<>();

        Course entity;
        for (CoursePojo pojo : pojos) {

            entity = pojoToEntity(pojo);
            entity.setOwner(userService.findByEmail(pojo.getOwner().getEmail()));
            entity.setCreatedBy(createdBy);
            entity.generatePublicKey();
            entities.add(entity);
        }

        entities = courseRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException("No such a list of courses is saved");
        }

        return true;
    }

    @Override
    public Map<String, Integer> getCourseStatus() {

        HashMap<String, Integer> statuses = new HashMap<>();

        int visibleCourses = courseRepository.countByVisible(true);
        int invisibleCourses = courseRepository.countByVisible(false);

        statuses.put("visibleCourses", visibleCourses);
        statuses.put("invisibleCourses", invisibleCourses);

        return statuses;
    }

    @Override
    public boolean codeAlreadyExist(String code) {
        return courseRepository.existsByCode(code);
    }

    @Override
    public boolean updateVisibility(String publicKey, boolean visible) throws ExecutionFailException, DataNotFoundException {

        User updatedBy = userDetailsService.getAuthenticatedUser();

        Course entity = findByPublicKey(publicKey);

        entity.setUpdatedBy(updatedBy);

        entity.setVisible(visible);

        entity = courseRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Setting course visibility is not executed successfully");
        }

        return true;
    }

    @Override
    public Course findByPublicKey(String publicKey) throws DataNotFoundException {

        Course course = courseRepository.findByPublicKey(publicKey);
        if (course == null) {
            throw new DataNotFoundException(String.format("No such course is found by publicKey: %s", publicKey));
        }
        return course;
    }

    @Override
    public boolean registerUserToCourse(Course course, User user) throws ExecutionFailException {

        User authUser = userDetailsService.getAuthenticatedUser();

        course.setUpdatedBy(authUser);
        course.getRegisteredUsers().add(user);
        course = courseRepository.save(course);

        if (course == null || course.getId() == 0) {
            throw new ExecutionFailException("No such user is registered to a course");
        }

        return true;
    }


    @Override
    public List<CoursePojo> getNotRegisteredCourses() throws DataNotFoundException {
        User authUser = userDetailsService.getAuthenticatedUser();

        List<Course> entities = courseRepository.findAllByRegisteredUsersNotInAndVisible(authUser, true);

        if (entities == null) {
            throw new DataNotFoundException("No such a registered courses found for authenticated users");
        }

        List<CoursePojo> pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());

        return pojos;
    }
}
