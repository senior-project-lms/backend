package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.CourseResource;
import com.lms.entities.course.StudentAssignment;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.pojos.course.StudentAssignmentPojo;
import com.lms.repositories.CourseResourceRepository;
import com.lms.repositories.StudentAssignmentRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseResourceService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.course.StudentAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentAssignmentServiceImpl implements StudentAssignmentService {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentAssignmentRepository studentAssignmentRepository;

    @Autowired
    private CourseAssignmentService courseAssignmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseResourceService resourceService;


    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Override
    public StudentAssignmentPojo entityToPojo(StudentAssignment entity) {
        StudentAssignmentPojo pojo = new StudentAssignmentPojo();
        pojo.setPublicKey(entity.getPublicKey());

        pojo.setContent(entity.getContent());

        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        pojo.setCreatedAt(entity.getCreatedAt());

        List<CourseResourcePojo> resourcePojos = new ArrayList<>();
        List<String> resourceKeys = new ArrayList<>();
        if (entity.getCourseResources() != null){
            for (CourseResource resource : entity.getCourseResources()){
                if (resource.isVisible()){
                    resourcePojos.add(resourceService.entityToPojo(resource));
                    resourceKeys.add(resource.getPublicKey());
                }
            }
        }
        pojo.setResourceKeys(resourceKeys);
        pojo.setResources(resourcePojos);
        return pojo;

    }

    @Override
    public StudentAssignment pojoToEntity(StudentAssignmentPojo pojo) {
       StudentAssignment entity = new StudentAssignment();

       entity.setContent(pojo.getContent());

        return entity;
    }

    @Override
    public List<StudentAssignmentPojo> getAllStudentAssignments(String assignmentPublicKey) throws DataNotFoundException {

        Assignment assignment = courseAssignmentService.findByPublicKey(assignmentPublicKey);

        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findAllByAssignment(assignment);

        List<StudentAssignmentPojo> pojos = studentAssignments
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public StudentAssignmentPojo getAuthStudentAssignment(String assignmentPublicKey) throws DataNotFoundException {
        Assignment assignment = courseAssignmentService.findByPublicKey(assignmentPublicKey);
        User authUser = customUserDetailService.getAuthenticatedUser();

        StudentAssignment entity = studentAssignmentRepository.findByAssignmentAndCreatedBy(assignment, authUser);
        if (entity == null){
            StudentAssignmentPojo p = new StudentAssignmentPojo();

            p.setContent("");
            p.setPublicKey("");
            p.setResourceKeys(new ArrayList<>());
            p.setResources(new ArrayList<>());
            return p;
        }
        return entityToPojo(entity);
    }

    @Override
    public StudentAssignmentPojo getByPublicKey(String publicKey) throws DataNotFoundException {

        StudentAssignment entity = studentAssignmentRepository.findByPublicKey(publicKey);

        if(entity == null){
            throw new DataNotFoundException("Student assignment is not found by public key");
        }

        return this.entityToPojo(entity);
    }




    @Override
    public SuccessPojo save(String assignmentPublicKey, StudentAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException {
        User authUser = customUserDetailService.getAuthenticatedUser();
        Assignment assignment = courseAssignmentService.findByPublicKey(assignmentPublicKey);

        StudentAssignment entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(authUser);
        entity.setAssignment(assignment);
        entity = studentAssignmentRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new  ExecutionFailException("No such a assignment asnswer is posted");
        }

        if (pojo.getResourceKeys() != null){
            for (String s : pojo.getResourceKeys()){
                resourceService.setResourceStudentAssignment(s, entity);
            }
        }

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo update(String publicKey, StudentAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException {

        User authUser = customUserDetailService.getAuthenticatedUser();
        StudentAssignment entity = studentAssignmentRepository.findByPublicKey(publicKey);

        entity.setContent(pojo.getContent());
        entity.setUpdatedBy(authUser);

        entity = studentAssignmentRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new  ExecutionFailException("No such a assignment asnswer is posted");
        }

        List<String> notIn = entity.getCourseResources()
                .stream()
                .filter(e -> e.isVisible() && !pojo.getResourceKeys().contains(e.getPublicKey()))
                .map(e -> e.getPublicKey())
                .collect(Collectors.toList());



        if (pojo.getResourceKeys() != null){

            for (String s: pojo.getResourceKeys()) {
                resourceService.setResourceStudentAssignment(s, entity);
            }
        }

        for (String s : notIn){
            resourceService.delete(s);
        }
        return new SuccessPojo(entity.getPublicKey());

    }
}

