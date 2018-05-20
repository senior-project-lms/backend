package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import com.lms.entities.course.Grade;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseAssignmentPojo;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.pojos.course.GradePojo;
import com.lms.repositories.CourseAssignmentRepository;
import com.lms.repositories.CourseResourceRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseGradeService;
import com.lms.services.interfaces.course.CourseResourceService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseAssignmentService;
import com.lms.services.interfaces.course.CourseService;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

    @Autowired
    private CourseGradeService courseGradeService;

    @Override
    public CourseAssignmentPojo entityToPojo(Assignment entity) {

        CourseAssignmentPojo pojo = new CourseAssignmentPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setTitle(entity.getTitle());
        pojo.setContent(entity.getContent());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        pojo.setLastDate(entity.getLastDate());
        pojo.setDueDate(entity.getDueDate());
        pojo.setDueUp(isDueUp(entity));
        List<CourseResourcePojo> resourcePojos = new ArrayList<>();

        List<String> resourceKeys = new ArrayList<>();

        if (entity.getCourseResources() != null) {
            for (CourseResource resourceEntity : entity.getCourseResources()) {
                if (resourceEntity.isVisible()) {
                    resourcePojos.add(courseResourceService.entityToPojo(resourceEntity));
                    resourceKeys.add(resourceEntity.getPublicKey());
                }
            }

        }

        pojo.setGradable(entity.isGradable());

        if (entity.isGradable() && entity.getGrade() != null && entity.getGrade().isVisible()){
            pojo.setGrade(courseGradeService.entityToPojo(entity.getGrade()));
        }
        else{
            GradePojo grade = new GradePojo();
            grade.setName("");
            pojo.setGrade(grade);
        }
        pojo.setResourceKeys(resourceKeys);
        pojo.setResources(resourcePojos);
        pojo.setPublished(entity.isPublished());
        return pojo;

    }

    @Override
    public Assignment pojoToEntity(CourseAssignmentPojo pojo) {
        Assignment entity = new Assignment();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());

        entity.setDueDate(pojo.getDueDate());
        entity.setLastDate(pojo.getLastDate());
        entity.setGradable(pojo.isGradable());



        if (pojo.getResources() != null){
            List<CourseResource> resourceEntities = new ArrayList<>();
            for(CourseResourcePojo resourcePojo : pojo.getResources()){
                resourceEntities.add(courseResourceService.pojoToEntity(resourcePojo));
            }
            entity.setCourseResources(resourceEntities);
        }


        return entity;

    }

    @Override
    public List<CourseAssignmentPojo> getAll(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<Assignment> courseAssignments = courseAssignmentRepository.findAllByCourseAndVisible(course,true);

        List<CourseAssignmentPojo> pojos = courseAssignments
                .stream()
                .map(entity -> {
                    CourseAssignmentPojo pojo = entityToPojo(entity);
                    pojo.setGrade(null);
                    pojo.setResources(null);
                    return pojo;
                })
                .collect(Collectors.toList());
        Collections.reverse(pojos);
        return pojos;

    }

    @Override
    public List<CourseAssignmentPojo> getAllForStudents(String publicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(publicKey);

        List<Assignment> courseAssignments = courseAssignmentRepository.findAllByCourseAndVisibleAndPublished(course,true, true);

        List<CourseAssignmentPojo> pojos = courseAssignments
                .stream()
                .map(entity -> {
                    CourseAssignmentPojo pojo = entityToPojo(entity);
                    pojo.setGrade(null);
                    pojo.setResources(null);
                    return pojo;
                })
                .collect(Collectors.toList());
        Collections.reverse(pojos);
        return pojos;

    }


    @Override
    public CourseAssignmentPojo get(String publicKey) throws DataNotFoundException {
        Assignment entity = courseAssignmentRepository.findByPublicKey(publicKey);

        if(entity == null){
            throw new DataNotFoundException("Course assignment is not found by public key");
        }

        return entityToPojo(entity);
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
    public boolean update(String publicKey, CourseAssignmentPojo pojo) throws ExecutionFailException, DataNotFoundException {
        User authUser = customUserDetailService.getAuthenticatedUser();
        Assignment entity = courseAssignmentRepository.findByPublicKey(publicKey);

        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());
        entity.setDueDate(pojo.getDueDate());
        entity.setLastDate(pojo.getLastDate());

        entity.setUpdatedBy(authUser);


        if (entity.isGradable() && !pojo.isGradable()){
            courseGradeService.delete(entity.getGrade().getPublicKey());
            entity.setGrade(null);
        }
        else if (!entity.isGradable() && pojo.isGradable() && pojo.getGrade() != null){
            SuccessPojo successPojo = courseGradeService.save(entity.getCourse().getPublicKey(), pojo.getGrade());
            Grade grade = courseGradeService.findByPublicKey(successPojo.getPublicKey());
            entity.setGrade(grade);
        }
        else if(entity.isGradable() && pojo.isGradable() && pojo.getGrade() != null){
            courseGradeService.update(pojo.getGrade().getPublicKey(), pojo.getGrade());
        }
        entity.setGradable(pojo.isGradable());


            entity = courseAssignmentRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Course assignment is not saved");
        }


        List<String> notIn = entity.getCourseResources()
                .stream()
                .filter(e -> e.isVisible() && !pojo.getResourceKeys().contains(e.getPublicKey()))
                .map(e -> e.getPublicKey())
                .collect(Collectors.toList());



        if (pojo.getResourceKeys() != null){

            for (String s: pojo.getResourceKeys()) {
                courseResourceService.setResourceAssignment(s, entity );
            }
        }

        for (String s : notIn){
            courseResourceService.delete(s);
        }


        return true;
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


        if (pojo.isGradable()){
            SuccessPojo successPojo = courseGradeService.save(coursePublicKey, pojo.getGrade());
            Grade grade = courseGradeService.findByPublicKey(successPojo.getPublicKey());
            entity.setGrade(grade);
        }

        entity = courseAssignmentRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Course assignment is not saved");
        }

        if (pojo.getResourceKeys() != null){

            for (String s: pojo.getResourceKeys()) {
                courseResourceService.setResourceAssignment(s, entity );
            }
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


    @Override
    public boolean publish(String publicKey, boolean status) throws DataNotFoundException, ExecutionFailException {

        Assignment entity = courseAssignmentRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new DataNotFoundException(String.format("No such a assignment is found publicKey: %s", publicKey));
        }

        entity.setPublished(status);

        entity = courseAssignmentRepository.save(entity);

        if (entity == null){
            throw new ExecutionFailException(String.format("No such a assignment is found publicKey: %s", publicKey));
        }

        return true;
    }

    private boolean isDueUp(Assignment entity) {

        if (entity == null) {
            return false;
        }


        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        long finishes = entity.getLastDate().getTime();
        long remaining = finishes - currentTime;

        if (remaining <= 0) {
            return true;
        }

        return false;

    }

    @Override
    public CourseAssignmentPojo getForAuthStudent(String publicKey) throws DataNotFoundException {
        Assignment entity = courseAssignmentRepository.findByPublicKey(publicKey);
        User authUser = customUserDetailService.getAuthenticatedUser();

        if(entity == null){
            throw new DataNotFoundException("Course assignment is not found by public key");
        }

        CourseAssignmentPojo pojo = entityToPojo(entity);

        pojo.setGrade(null);

        return pojo;
    }
}
