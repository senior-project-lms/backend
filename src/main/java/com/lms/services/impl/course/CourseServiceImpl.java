package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.EnrollmentRequest;
import com.lms.enums.AccessLevel;
import com.lms.pojos.UserPojo;
import com.lms.pojos.course.CoursePojo;
import com.lms.repositories.CourseRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.EnrollmentRequestService;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private EnrollmentRequestService enrollmentRequestService;

    @Autowired
    private UserCoursePrivilegeService userCoursePrivilegeService;


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
        entity.setName(pojo.getName().toUpperCase());
        entity.setCode(pojo.getCode().toUpperCase());
        return entity;
    }


    /**
     * return courses by visibility
     *
     * @param visible
     * @return List<CoursePojo>
     * @author umit.kas
     */
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

    /**
     * return course pojo by publicKey
     *
     * @param publicKey
     * @return CoursePojo
     * @author umit.kas
     */
    @Override
    public CoursePojo getByPublicKey(String publicKey) throws DataNotFoundException {

        Course entity = findByPublicKey(publicKey);

        CoursePojo pojo = entityToPojo(entity);

        return pojo;
    }


    /**
     * save course
     *
     * @param pojo
     * @return List<CoursePojo>
     * @author umit.kas
     */

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


    /**
     * save course collections
     *
     * @param pojos
     * @return List<CoursePojo>
     * @author umit.kas
     */
    @Transactional
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

        userCoursePrivilegeService.saveCourseLecturerPrivileges(entities);

        return true;
    }


    /**
     * return course counts by visibility
     *
     * @param
     * @return Map<String ,   Integer>
     * @author umit.kas
     */

    @Override
    public Map<String, Integer> getCourseStatus() {

        HashMap<String, Integer> statuses = new HashMap<>();

        int visibleCourses = courseRepository.countByVisible(true);
        int invisibleCourses = courseRepository.countByVisible(false);

        statuses.put("visibleCourses", visibleCourses);
        statuses.put("invisibleCourses", invisibleCourses);

        return statuses;
    }

    /**
     * checks course code is already exist or not
     *
     * @param code
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean codeAlreadyExist(String code) {
        return courseRepository.existsByCode(code);
    }


    /**
     * update the visibility of course by publicKey
     *
     * @param publicKey
     * @param visible
     * @return boolean
     * @author umit.kas
     */
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

        enrollmentRequestService.updateVisibilityByCourse(entity, visible);

        return true;
    }

    /**
     * return course entity by publicKey
     *
     * @param publicKey
     * @return Course
     * @author umit.kas
     */
    @Override
    public Course findByPublicKey(String publicKey) throws DataNotFoundException {

        Course course = courseRepository.findByPublicKey(publicKey);
        if (course == null) {
            throw new DataNotFoundException(String.format("No such course is found by publicKey: %s", publicKey));
        }
        return course;
    }


    /**
     * adds the users to course registered user collection
     *
     * @param course
     * @param users
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean registerUsersToCourse(Course course, List<User> users) throws ExecutionFailException {

        User authUser = userDetailsService.getAuthenticatedUser();

        course.setUpdatedBy(authUser);
        course.getRegisteredUsers().addAll(users);
        course = courseRepository.save(course);

        if (course == null || course.getId() == 0) {
            throw new ExecutionFailException("No such user is registered to a course");
        }

        return true;
    }




    /**
     * returns not registered courses by authenticated and search param code
     *
     * @return List<CoursePojo>
     * @author umit.kas
     */
    @Override
    public List<CoursePojo> getNotRegisteredCoursesByCodeByAuthUser(String param) throws DataNotFoundException {
        param = param.toUpperCase();
        User authUser = userDetailsService.getAuthenticatedUser();

        List<Course> entities = courseRepository.findAllByRegisteredUsersNotContainsAndVisibleAndCodeContaining(authUser, true, param);

        if (entities == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        return getNotRegisteredCoursesHelperForAuthUser(entities);
    }

    /**
     * returns not registered courses by authenticated and search param name
     *
     * @return List<CoursePojo>
     * @author umit.kas
     */
    @Override
    public List<CoursePojo> getNotRegisteredCoursesByNameByAuthUser(String param) throws DataNotFoundException {
        param = param.toUpperCase();

        User authUser = userDetailsService.getAuthenticatedUser();

        List<Course> entities = courseRepository
                .findAllByRegisteredUsersNotContainsAndVisibleAndNameContaining(authUser, true, param);

        if (entities == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        return getNotRegisteredCoursesHelperForAuthUser(entities);
    }


    /**
     * returns not registered courses by authenticated and search param lecturer
     *
     * @return List<CoursePojo>
     * @author umit.kas
     */
    @Override
    public List<CoursePojo> getNotRegisteredCoursesByLecturerByAuthUser(String name, String surname) throws DataNotFoundException {
        if (name != null)
            name = name.toUpperCase();
        else if (surname != null)
            surname = surname.toUpperCase();


        User authUser = userDetailsService.getAuthenticatedUser();
        List<User> lecturers = userService.findAllByNameOrSurname(name, surname);
        List<Course> entities = courseRepository
                .findAllByRegisteredUsersNotContainsAndVisibleAndOwnerIn(authUser, true, lecturers);

        if (entities == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        return getNotRegisteredCoursesHelperForAuthUser(entities);
    }


    private List<CoursePojo> getNotRegisteredCoursesHelperForAuthUser(List<Course> entities) throws DataNotFoundException {

        List<EnrollmentRequest> enrolmentRequestsEntities = enrollmentRequestService.findEnrollmentRequestsOfAuthUser(true);
        List<String> enrollmentRequestedCoursesPublicKeys = enrolmentRequestsEntities
                .stream()
                .filter(entity -> entity.isPending() || entity.isEnrolled())
                .map(entity -> entity.getCourse().getPublicKey())
                .collect(Collectors.toList());


        List<CoursePojo> pojos = entities
                .stream()
                .filter(entity -> !enrollmentRequestedCoursesPublicKeys.contains(entity.getPublicKey()))
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }


    // TODO: 24.02.2018 course a ait authenticated user icin(asistan veya yetkili user) courselar cekilmedi
    @Override
    public List<CoursePojo> getAuthUserCourses() throws DataNotFoundException {

        User authUser = userDetailsService.getAuthenticatedUser();

        List<Course> entities = null;

        if (authUser.getAuthority().getCode() == AccessLevel.LECTURER.CODE) {
            entities = courseRepository.findAllByOwnerAndVisible(authUser, true);
        }
        else {
            entities = courseRepository.findAllByRegisteredUsersContainsAndVisible(authUser, true);
        }


        if (entities == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        List<CoursePojo> pojos = entities
                .stream()
                .map(entity -> {
                    CoursePojo pojo = entityToPojo(entity);
                    pojo.setOwner(null);
                    pojo.setCreatedAt(null);
                    pojo.setUpdatedAt(null);
                    return pojo;
                })
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public List<UserPojo> getEnrolledUsers(String publicKey) throws DataNotFoundException {
        Course entity = courseRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        List<UserPojo> pojos = entity.getRegisteredUsers()
                .stream()
                .map(user -> {
                    UserPojo pojo = userService.entityToPojo(user);
                    pojo.setAuthority(null);
                    return pojo;
                })
                .collect(Collectors.toList());

        return pojos;
    }
}
