package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import com.lms.pojos.course.CourseAssignmentPojo;
import com.lms.repositories.CourseAssignmentRepository;
import com.lms.repositories.CourseResourceRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public CourseAssignmentPojo entityToPojo(Assignment entity) {

        CourseAssignmentPojo pojo = new CourseAssignmentPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setTitle(entity.getTitle());
        pojo.setContent(entity.getContent());
        pojo.setOriginalFileName(entity.getOriginalFileName());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));

        return pojo;

    }

    @Override
    public Assignment pojoToEntity(CourseAssignmentPojo pojo) {
        Assignment entity = new Assignment();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity.setOriginalFileName(pojo.getOriginalFileName());

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

    /**
     * map the paramater course assignment with course resource
     * finds the resource by publicKey then adds the assignment as paramater
     * then call repository save method to update

     * @author emsal aynaci
     * @param publicKey
     * @param assignment
     * @return boolean
     */

    @Override
    public boolean setResourceAssignment(String publicKey, Assignment assignment) throws ExecutionFailException, DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new DataNotFoundException(String.format("Course resource is not added to for assignment publicKey: %s", publicKey));
        }

        entity.setCourseAssignment(assignment);
        entity = courseResourceRepository.save(entity);

        if (entity != null && entity.getId() == 0){
            throw new ExecutionFailException("No such a course assignment of course resource is saved");
        }

        return true;

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

    @Override
    public boolean save(String coursePublicKey, CourseAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException {
        User createdBy = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        Assignment entity = this.pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setCourse(course);

        entity.setCreatedBy(createdBy);
        entity = courseAssignmentRepository.save(entity);
        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Course assignment is not saved");
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
