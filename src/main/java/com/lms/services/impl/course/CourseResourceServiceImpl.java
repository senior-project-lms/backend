package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.repositories.CourseResourceRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseResourceService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseResourceServiceImpl implements CourseResourceService {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseAssignmentService courseAssignmentService;

    @Autowired
    private CourseResourceRepository courseResourceRepository;


    /**
     * Converts CourseResource entity to CourseResourcePojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author emsal aynaci
     * @param entity
     * @return CourseResourcePojo
     */


    @Override
    public CourseResourcePojo entityToPojo(CourseResource entity) {
        CourseResourcePojo pojo = new CourseResourcePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setType(entity.getType());
        pojo.setUrl(entity.getUrl());
        pojo.setOriginalFileName(entity.getOriginalFileName());
        return pojo;

    }


    /**
     * Converts CourseResourcePojo to CourseResource  according to values, if the value is null passes it,

     * @author emsal aynaci
     * @param pojo
     * @return CourseResource
     */

    @Override
    public CourseResource pojoToEntity(CourseResourcePojo pojo) {
        CourseResource entity = new CourseResource();

        entity.setName(pojo.getName());
        entity.setPath(pojo.getPath());
        entity.setType(pojo.getType());
        entity.setOriginalFileName(pojo.getOriginalFileName());
        entity.setUrl(pojo.getUrl());
        entity.setPublicKey(pojo.getPublicKey());

        return  entity;

    }

    /**
     * Save entity list to database.

     * @author emsal aynaci
     * @param resources
     * @return boolean
     */
    @Override
    public boolean saveEntities(List<CourseResource> resources) {
        resources.stream().map(resource -> {
            resource.generatePublicKey();
            return resource;
        });

        courseResourceRepository.save(resources);
        return true;
    }


    /**
     * Saves the resource to db,
     * convert pojo to entity, generates publicKey, set visible,
     *
     * @param pojo
     * @return boolean
     * @author emsal aynaci
     */
    @Override
    public boolean save(String coursePublicKey, CourseResourcePojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);
        CourseResource entity = pojoToEntity(pojo);
        entity.generatePublicKey();

        entity.setCourse(course);
        entity.setCreatedBy(authenticatedUser);

        entity = courseResourceRepository.save(entity);

        if (entity == null || entity.getId() == 0) {

            throw new ExecutionFailException("No such a resource is saved");
        }

        return true;

    }

    @Override
    public boolean save(List<CourseResourcePojo> pojos) {
        return false;
    }

    @Override
    public List<CourseResourcePojo> getCourseResources(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<CourseResource> courseResources = courseResourceRepository.findAllByCourseAndVisible(course,true);

        List<CourseResourcePojo> pojos = courseResources
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;

    }


    /**
     * finds the resource by name, if it is not null then converts it to pojo, and returns it.
     *
     * @author emsal aynaci
     * @param name
     * @return CourseResourcePojo
     */

    @Override
    public CourseResourcePojo getByName(String name) throws DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByName(name);

        if(entity == null){
            throw new DataNotFoundException("Course resource is not found by public key");
        }

        return this.entityToPojo(entity);

    }

    /**
     * finds the resource by publicKey, if it is not null then converts it to pojo, and returns it.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return CourseResourcePojo
     */

    @Override
    public CourseResourcePojo getByPublicKey(String publicKey) throws DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if(entity == null){
            throw new DataNotFoundException("Course resource is not found by public key");
        }

        return this.entityToPojo(entity);
    }

    /**
     * finds the resource by publicKey
     * if it is not null then
     * set visibility to false
     * and call save method of repository.
     *
     * @author emsal aynaci
     * @param publicKey
     * @return boolean
     */
    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new DataNotFoundException(String.format("No such a course resource is found publicKey: %s", publicKey));
        }
        entity.setVisible(false);
        entity = courseResourceRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException(String.format("Course resource is not deleted by publicKey: %s", publicKey));
        }
        return true;

    }
}
