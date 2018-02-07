package com.lms.services.impl.course;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.enums.ExceptionType;
import com.lms.pojos.course.CoursePojo;
import com.lms.repositories.CourseRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public List<CoursePojo> getAllByVisible(boolean visible) throws ServiceException {
        List<CoursePojo> pojos;

        List<Course> entities = courseRepository.findAllByVisible(visible);

        if (entities == null) {
            throw new ServiceException(ExceptionType.NO_SUCH_DATA_NOT_FOUND, "No such a course list is fetched");
        }

        pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());

        return pojos;
    }

    @Override
    public CoursePojo getByPublicKey(String publicKey) throws ServiceException {

        Course entity = courseRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new ServiceException(ExceptionType.NO_SUCH_DATA_NOT_FOUND, String.format("No such a course is found by publicKey: %s", publicKey));
        }

        CoursePojo pojo = entityToPojo(entity);

        return pojo;
    }

    @Override
    public boolean save(CoursePojo pojo) throws ServiceException {

        User owner = userService.findByEmail(pojo.getOwner().getEmail());

        Course entity = pojoToEntity(pojo);

        entity.setOwner(owner);

        entity.setCreatedBy(userDetailsService.getAuthenticatedUser());
        entity.generatePublicKey();
        entity = courseRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "No such a course is saved");
        }

        return true;
    }

    @Override
    public boolean save(List<CoursePojo> pojos) throws ServiceException {

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
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "No such a list of courses is saved");
        }

        return true;
    }

    @Override
    public Map<String, Integer> getCourseStatus() throws ServiceException {

        HashMap<String, Integer> statuses = new HashMap<>();

        int visibleCourses = courseRepository.findAllByVisible(true).size();
        int invisbleCourses = courseRepository.findAllByVisible(false).size();

        statuses.put("visibleCourses", visibleCourses);
        statuses.put("invisibleCourses", invisbleCourses);

        return statuses;
    }

    @Override
    public boolean codeAlreadyExist(String code) {

        return courseRepository.existsByCode(code);


    }
}
