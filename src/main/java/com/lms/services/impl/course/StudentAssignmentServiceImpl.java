package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.CourseResource;
import com.lms.entities.course.StudentAssignment;
import com.lms.pojos.course.StudentAssignmentPojo;
import com.lms.repositories.CourseResourceRepository;
import com.lms.repositories.StudentAssignmentRepository;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import com.lms.services.interfaces.course.StudentAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CourseResourceRepository courseResourceRepository;



    @Override
    public StudentAssignmentPojo entityToPojo(StudentAssignment entity) {
        StudentAssignmentPojo pojo = new StudentAssignmentPojo();

        pojo.setContent(entity.getContent());

        return pojo;

    }

    @Override
    public StudentAssignment pojoToEntity(StudentAssignmentPojo pojo) {
       StudentAssignment entity = new StudentAssignment();

       entity.setContent(pojo.getContent());

        return entity;
    }

    @Override
    public List<StudentAssignmentPojo> getStudentAssignmentsByAssignmentPublicKey(String assignmentPublicKey) throws DataNotFoundException {

        Assignment assignment = courseAssignmentService.findByPublicKey(assignmentPublicKey);

        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findAllByAssignment(assignment);

        List<StudentAssignmentPojo> pojos = studentAssignments
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public StudentAssignmentPojo getByPublicKey(String publicKey) throws DataNotFoundException {

        StudentAssignment entity = studentAssignmentRepository.findByPublicKey(publicKey);

        if(entity == null){
            throw new DataNotFoundException("Student assignment is not found by public key");
        }

        return this.entityToPojo(entity);
    }

    /**
     * map the paramater student assignment with course resource
     * finds the resource by publicKey then adds the student assignment as paramater
     * then call repository save method to update

     * @author emsal aynaci
     * @param publicKey
     * @param studentAssignment
     * @return boolean
     */

    @Override
    public boolean setResourceStudentAssignment(String publicKey, StudentAssignment studentAssignment) throws ExecutionFailException, DataNotFoundException {
        CourseResource entity = courseResourceRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("Course resource is not added to for assignment publicKey: %s", publicKey));
        }

        entity.setStudentAssignment(studentAssignment);
        entity = courseResourceRepository.save(entity);

        if (entity != null && entity.getId() == 0) {
            throw new ExecutionFailException("No such a course assignment of course resource is saved");
        }

        return true;
    }
}

