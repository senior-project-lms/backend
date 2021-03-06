package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import com.lms.entities.course.StudentAssignment;
import com.lms.pojos.course.CourseAnnouncementPojo;
import com.lms.pojos.course.CoursePojo;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.repositories.CourseResourceRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseResourceService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseResourceServiceImpl implements CourseResourceService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseAssignmentService courseAssignmentService;

    @Autowired
    private CourseResourceRepository courseResourceRepository;

    @Autowired
    private CourseResourceService resourceService;

    /**
     * Converts CourseResource entity to CourseResourcePojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param entity
     * @return CourseResourcePojo
     * @author emsal aynaci
     */


    @Override
    public CourseResourcePojo entityToPojo(CourseResource entity) {
        CourseResourcePojo pojo = new CourseResourcePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setType(entity.getType());
        pojo.setUrl(entity.getUrl());
        pojo.setOriginalFileName(entity.getOriginalFileName());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        pojo.setPublicShared(entity.isPublicShared());
        return pojo;

    }


    /**
     * Converts CourseResourcePojo to CourseResource  according to values, if the value is null passes it,
     *
     * @param pojo
     * @return CourseResource
     * @author emsal aynaci
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
        entity.setResource(pojo.isResource());

        return entity;

    }

    /**
     * Save entity list to database.
     *
     * @param resources
     * @return boolean
     * @author emsal aynaci
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


    /**
     * save course resource collections
     *
     * @param pojos
     * @return List<CourseResourcePojo>
     * @author emsal aynaci
     */
    @Override
    public boolean save(List<CourseResourcePojo> pojos) throws DataNotFoundException, ExecutionFailException {

        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        List<CourseResource> entities = new ArrayList<>();

        CourseResource entity;
        for (CourseResourcePojo pojo : pojos) {

            entity = pojoToEntity(pojo);
            entity.setCreatedBy(authenticatedUser);
            entity.generatePublicKey();
            entities.add(entity);
        }

        entities = courseResourceRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException("No such a list of course resources is saved");
        }

        return true;

    }

    @Override
    public List<CourseResourcePojo> getCourseResources(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<CourseResource> courseResources = courseResourceRepository.findAllByCourseAndVisibleAndResource(
                course, true, true);

        List<CourseResourcePojo> pojos = courseResources
                .stream()
                //.filter(entity -> entity.isResource())
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());
        //Collections.reverse(pojos);
        return pojos;

    }

    @Override
    public boolean setResourceAssignment(String publicKey, Assignment assignment) throws ExecutionFailException, DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("Course resource is not added to for assignment publicKey: %s", publicKey));
        }

        entity.setCourseAssignment(assignment);
        entity = courseResourceRepository.save(entity);

        if (entity != null && entity.getId() == 0) {
            throw new ExecutionFailException("No such a course assignment of course resource is saved");
        }

        return true;
    }

    @Override
    public boolean setResourceStudentAssignment(String publicKey, StudentAssignment assignment) throws ExecutionFailException, DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("Course resource is not added to for assignment publicKey: %s", publicKey));
        }

        entity.setStudentAssignment(assignment);
        entity = courseResourceRepository.save(entity);

        if (entity != null && entity.getId() == 0) {
            throw new ExecutionFailException("No such a course assignment of course resource is saved");
        }

        return true;
    }

    /**
     * finds the resource by name, if it is not null then converts it to pojo, and returns it.
     *
     * @param name
     * @return CourseResourcePojo
     * @author emsal aynaci
     */

    @Override
    public CourseResourcePojo getByName(String name) throws DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByName(name);

        if (entity == null) {
            throw new DataNotFoundException("Course resource is not found by public key");
        }

        return this.entityToPojo(entity);

    }

    /**
     * finds the resource by publicKey, if it is not null then converts it to pojo, and returns it.
     *
     * @param publicKey
     * @return CourseResourcePojo
     * @author emsal aynaci
     */

    @Override
    public CourseResourcePojo getByPublicKey(String publicKey) throws DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException("Course resource is not found by public key");
        }

        return this.entityToPojo(entity);
    }

    @Override
    public boolean publiclyShared(String publicKey, boolean status) throws DataNotFoundException, ExecutionFailException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        entity.setPublicShared(status);

        entity = courseResourceRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a course resource is shared");
        }

        return true;
    }


    /**
     * finds the resource by publicKey
     * if it is not null then
     * set visibility to false
     * and call save method of repository.
     *
     * @param publicKey
     * @return boolean
     * @author emsal aynaci
     */
    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a course resource is found publicKey: %s", publicKey));
        }
        entity.setVisible(false);
        entity = courseResourceRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException(String.format("Course resource is not deleted by publicKey: %s", publicKey));
        }
        return true;
    }


    @Override
    public List<CourseResource> findByPublicKeys(List<String> publicKeys) {
        List<CourseResource> entities = courseResourceRepository.findAllByPublicKeyInAndVisible(publicKeys, true);

        if (entities == null) {
            return new ArrayList<>();
        }

        return entities;

    }

    @Override
    public List<CoursePojo> getAllPublicResources() throws ExecutionFailException, DataNotFoundException {
        List<CoursePojo> pojos = new ArrayList<>();
        List<String> publicKeys = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        List<CourseResource> publicResources = courseResourceRepository.findAllByPublicSharedAndResourceAndVisible(true,true, true);

        for (CourseResource courseResource : publicResources) {
            if (!publicKeys.contains(courseResource.getCourse().getPublicKey())) {
                courses.add(courseResource.getCourse());
                publicKeys.add(courseResource.getCourse().getPublicKey());
            }
        }

        for (Course course : courses){
            CoursePojo coursePojo = courseService.entityToPojo(course);
            if (course.getResources() != null){
                List<CourseResourcePojo> resourcePojos = course.getResources()
                        .stream()
                        .filter(e -> e.isVisible() && e.isPublicShared())
                        .map(e -> resourceService.entityToPojo(e))
                        .collect(Collectors.toList());
                coursePojo.setResources(resourcePojos);
            }
            pojos.add(coursePojo);
        }
        return pojos;
    }
}
