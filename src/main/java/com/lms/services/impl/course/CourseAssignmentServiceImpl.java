package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import com.lms.pojos.course.CourseAssignmentPojo;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.repositories.CourseAssignmentRepository;
import com.lms.repositories.CourseResourceRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseResourceService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

    @Autowired
    private UserService userService;


    @Autowired
    private CourseAssignmentRepository courseAssignmentRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseResourceRepository courseResourceRepository;

    @Autowired
    private CourseResourceService courseResourceService;


    @Override
    public CourseAssignmentPojo entityToPojo(Assignment entity) {

        CourseAssignmentPojo pojo = new CourseAssignmentPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setTitle(entity.getTitle());
        pojo.setContent(entity.getContent());
        pojo.setOriginalFileName(entity.getOriginalFileName());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));

        List<CourseResourcePojo> resourcePojos = new ArrayList<>();
        for (CourseResource resourceEntity : entity.getCourseResources()) {
            resourcePojos.add(courseResourceService.entityToPojo(resourceEntity));
        }
        pojo.setCourseResources(resourcePojos);

        return pojo;

    }

    @Override
    public Assignment pojoToEntity(CourseAssignmentPojo pojo) {
        Assignment entity = new Assignment();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity.setOriginalFileName(pojo.getOriginalFileName());

        if (pojo.getCourseResources() != null){
            List<CourseResource> resourceEntities = new ArrayList<>();
            for(CourseResourcePojo resourcePojo : pojo.getCourseResources()){
                resourceEntities.add(courseResourceService.pojoToEntity(resourcePojo));
            }
            entity.setCourseResources(resourceEntities);
        }


        return entity;

    }

    @Override
    public List<CourseAssignmentPojo> getAllAssignmentsOfCourse(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<Assignment> courseAssignments = courseAssignmentRepository.findAllByCourseAndVisible(course,true);

        List<CourseAssignmentPojo> pojos = courseAssignments
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;

    }

    @Override
    public CourseAssignmentPojo getByPublicKey(String publicKey) throws DataNotFoundException {
        Assignment entity = courseAssignmentRepository.findByPublicKey(publicKey);

        if(entity == null){
            throw new DataNotFoundException("Course assignment is not found by public key");
        }

        return this.entityToPojo(entity);
    }

    @Override
    public Assignment findByPublicKey(String publicKey) throws DataNotFoundException {

        Assignment assignment = courseAssignmentRepository.findByPublicKey(publicKey);

        if(assignment == null){
            throw new DataNotFoundException(String.format("No such assignment is found by publicKey: %s", publicKey));
        }

        return assignment;
    }


    /**
     *
     * Save the CourseAssignment, converts input pojo to entity, also converts resources to entity
     * add who creates(authenticated user)
     * generates publicKey
     * put resources to a list
     * then set null resource list in entity
     * set visible to entity
     * save entity
     * then iterate resources, add who create it, visibility and assignment, which is saved
     * then save the resources by its service

     * @author emsal aynaci
     * @param pojo
     * @return boolean
     */
    @Override
    public boolean save(String coursePublicKey, CourseAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException {

        List<String> resourceKeys = pojo.getResourceKeys();

        User createdBy = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        Assignment entity = this.pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setCourse(course);

        entity.setCreatedBy(createdBy);

        entity.setCourseResources(null);
        entity = courseAssignmentRepository.save(entity);
        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Course assignment is not saved");
        }

        for(String key: resourceKeys){
            courseResourceService.setResourceAssignment(key,entity);
        }
        return true;

    }

    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        Assignment entity = courseAssignmentRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new DataNotFoundException(String.format("No such a assignment is found publicKey: %s", publicKey));
        }
        entity.setVisible(false);
        entity = courseAssignmentRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException(String.format("Course assignment is not deleted by publicKey: %s", publicKey));
        }
        return true;

    }
}