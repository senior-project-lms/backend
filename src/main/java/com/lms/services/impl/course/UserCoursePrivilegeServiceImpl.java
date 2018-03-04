package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.UserCoursePrivilege;
import com.lms.enums.AccessLevel;
import com.lms.enums.ECoursePrivilege;
import com.lms.enums.courseUserPrivileges.ECourseAssistantPrivilege;
import com.lms.enums.courseUserPrivileges.ECourseLecturerPrivilege;
import com.lms.enums.courseUserPrivileges.ECourseObserverPrivilege;
import com.lms.enums.courseUserPrivileges.ECourseStudentPrivilege;
import com.lms.pojos.PrivilegePojo;
import com.lms.pojos.course.UserCoursePrivilegePojo;
import com.lms.repositories.UserCoursePrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseService;
import com.lms.services.interfaces.PrivilegeService;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserCoursePrivilegeServiceImpl implements UserCoursePrivilegeService {

    @Autowired
    private UserCoursePrivilegeRepository userCoursePrivilegeRepository;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Override
    public UserCoursePrivilegePojo entityToPojo(UserCoursePrivilege entity) {
        UserCoursePrivilegePojo pojo = new UserCoursePrivilegePojo();

        pojo.setUser(userService.entityToPojo(entity.getUser()));

        List<PrivilegePojo> privilegePojos = new ArrayList<>();
        for (Privilege privilege : entity.getPrivileges()) {
            privilegePojos.add(privilegeService.entityToPojo(privilege));
        }
        pojo.setPrivileges(privilegePojos);

        return pojo;
    }

    @Override
    public UserCoursePrivilege pojoToEntity(UserCoursePrivilegePojo pojo) {
        UserCoursePrivilege entity = new UserCoursePrivilege();

        return entity;
    }

    @Override
    public List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible) throws DataNotFoundException {

        List<UserCoursePrivilege> entities = userCoursePrivilegeRepository.findAllByUserAndVisible(user, visible);

        if (entities == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        return entities;
    }

    @Override
    public boolean existByUser(User user) {
        return userCoursePrivilegeRepository.existsByUser(user);
    }


    @Transactional
    @Override
    public boolean saveStudentCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();
        List<Privilege> privileges = null;
        privileges = privilegeService.findAllByCode(getStudentDefaultPrivilegeCodes());


        List<UserCoursePrivilege> entities = new ArrayList<>();

        UserCoursePrivilege entity = null;

        for (User user : users){
            entity = new UserCoursePrivilege();
            entity.generatePublicKey();
            entity.setCreatedBy(authUser);
            entity.setUser(user);
            entity.setCourse(course);
            entity.setPrivileges(privileges);
            entities.add(entity);
        }

        entities = userCoursePrivilegeRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course %s is saved", course.getCode()));
        }

        return true;
    }

    @Transactional
    @Override
    public boolean saveCourseLecturerPrivileges(List<Course> courses) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailService.getAuthenticatedUser();

        List<Privilege> privileges = privilegeService.findAllByCode(getLecturerDefaultPrivilegeCodes());

        List<UserCoursePrivilege> entities = new ArrayList<>();

        UserCoursePrivilege entity = null;

        for (Course course: courses){
            entity = new UserCoursePrivilege();
            entity.generatePublicKey();
            entity.setCreatedBy(authUser);
            entity.setUser(course.getOwner());
            entity.setCourse(course);
            entity.setPrivileges(privileges);
            entities.add(entity);
        }

        entities = userCoursePrivilegeRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course  is saved"));
        }

        return true;
    }

    @Override
    public List<Long> getAllCoursePrivilegeCodes() {
        List<Long> privilegeCodes = new ArrayList<>();

        for (ECoursePrivilege eCoursePrivilege : ECoursePrivilege.values()){
            privilegeCodes.add(eCoursePrivilege.CODE);
        }

        return privilegeCodes;
    }

    @Override
    public List<Long> getLecturerDefaultPrivilegeCodes() {
        List<Long> privilegeCodes = new ArrayList<>();

        for (ECourseLecturerPrivilege eCourseLecturerPrivilege: ECourseLecturerPrivilege.values()){
            privilegeCodes.add(eCourseLecturerPrivilege.CODE);
        }

        return privilegeCodes;
    }

    @Override
    public List<Long> getAssistantDefaultPrivilegeCodes() {
        List<Long> privilegeCodes = new ArrayList<>();

        for (ECourseAssistantPrivilege eCourseAssistantPrivilege: ECourseAssistantPrivilege.values()){
            privilegeCodes.add(eCourseAssistantPrivilege.CODE);
        }

        return privilegeCodes;
    }

    @Override
    public List<Long> getStudentDefaultPrivilegeCodes() {
        List<Long> privilegeCodes = new ArrayList<>();

        for (ECourseStudentPrivilege eCourseStudentPrivilege: ECourseStudentPrivilege.values()){
            privilegeCodes.add(eCourseStudentPrivilege.CODE);
        }

        return privilegeCodes;
    }


    @Override
    public List<Long> getObserverDefaultPrivilegeCodes() {
        List<Long> privilegeCodes = new ArrayList<>();

        for (ECourseObserverPrivilege eCourseObserverPrivilege : ECourseObserverPrivilege.values()) {
            privilegeCodes.add(eCourseObserverPrivilege.CODE);
        }

        return privilegeCodes;
    }


    @Override
    public List<Long> getCoursePrivilegesOfAuthUser(String coursePublicKey) throws DataNotFoundException {
        User authUser = userDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        UserCoursePrivilege userCoursePrivilege = userCoursePrivilegeRepository.findByCourseAndUser(course, authUser);

        List<Long> privilegeCodes = userCoursePrivilege.getPrivileges()
                .stream()
                .map(entity -> entity.getCode())
                .collect(Collectors.toList());

        return privilegeCodes;
    }

    @Override
    public boolean hasPrivilege(String coursePublicKey, ECoursePrivilege coursePrivilege) throws DataNotFoundException {
        User authUser = userDetailService.getAuthenticatedUser();

        Course course = courseService.findByPublicKey(coursePublicKey);
        Privilege privilege = privilegeService.findAllByCode(Arrays.asList(coursePrivilege.CODE)).get(0);

        if (course != null && privilege != null){

            return userCoursePrivilegeRepository.existsByUserAndCourseAndPrivilegesContaining(authUser, course, privilege);
        }

        return false;
    }


    @Override
    public List<UserCoursePrivilegePojo> getAssistantUsersOfCourse(String publicKey) throws DataNotFoundException {

        Course course = courseService.findByPublicKey(publicKey);
        List<UserCoursePrivilege> userCoursePrivileges = userCoursePrivilegeRepository.findAllByUserInAndVisible(course.getAssistantUsers(), false);

        List<UserCoursePrivilegePojo> pojos = userCoursePrivileges
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());
        return pojos;
    }


    @Override
    public boolean saveAssistantCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();
        List<Privilege> privileges = null;
        privileges = privilegeService.findAllByCode(getAssistantDefaultPrivilegeCodes());


        List<UserCoursePrivilege> entities = new ArrayList<>();

        UserCoursePrivilege entity = null;

        for (User user : users) {
            entity = new UserCoursePrivilege();
            entity.generatePublicKey();
            entity.setCreatedBy(authUser);
            entity.setUser(user);
            entity.setCourse(course);
            entity.setPrivileges(privileges);
            entities.add(entity);
        }

        entities = userCoursePrivilegeRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course %s is saved", course.getCode()));
        }

        return true;
    }

    @Override
    public boolean saveObserverUserCoursePrivileges(List<User> users, Course course) throws DataNotFoundException, ExecutionFailException {

        User authUser = userDetailService.getAuthenticatedUser();
        List<Privilege> privileges = null;
        privileges = privilegeService.findAllByCode(getObserverDefaultPrivilegeCodes());


        List<UserCoursePrivilege> entities = new ArrayList<>();

        UserCoursePrivilege entity = null;

        for (User user : users) {
            entity = new UserCoursePrivilege();
            entity.generatePublicKey();
            entity.setCreatedBy(authUser);
            entity.setUser(user);
            entity.setCourse(course);
            entity.setPrivileges(privileges);
            entities.add(entity);
        }

        entities = userCoursePrivilegeRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course %s is saved", course.getCode()));
        }

        return true;
    }

    @Override
    public boolean saveUserCoursePrivilege(Course course, UserCoursePrivilegePojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailService.getAuthenticatedUser();


        UserCoursePrivilege entity = new UserCoursePrivilege();

        entity.setUser(userService.findByPublicKey(pojo.getUser().getPublicKey()));
        entity.setCourse(course);

        List<String> privilegePublicKeys = pojo
                .getPrivileges()
                .stream()
                .map(privilege -> privilege.getPublicKey())
                .collect(Collectors.toList());


        List<Privilege> privileges = privilegeService.findAllByPublicKeys(privilegePublicKeys);

        entity.setPrivileges(privileges);
        entity.setUpdatedBy(authUser);
        entity.generatePublicKey();

        entity = userCoursePrivilegeRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course %s is saved", course.getCode()));
        }

        return true;
    }

    @Override
    public boolean updateUserCoursePrivilege(String coursePublicKey, UserCoursePrivilegePojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailService.getAuthenticatedUser();

        Course course = courseService.findByPublicKey(coursePublicKey);

        UserCoursePrivilege entity = userCoursePrivilegeRepository.findByCourseAndPublicKey(course, pojo.getPublicKey());


        List<String> privilegePublicKeys = pojo
                .getPrivileges()
                .stream()
                .map(privilege -> privilege.getPublicKey())
                .collect(Collectors.toList());


        List<Privilege> privileges = privilegeService.findAllByPublicKeys(privilegePublicKeys);

        entity.setPrivileges(privileges);

        entity.setUpdatedBy(authUser);

        entity = userCoursePrivilegeRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course %s is saved", course.getCode()));
        }

        return true;
    }

    @Override
    public boolean deleteUserCoursePrivilege(Course course, User user) throws DataNotFoundException, ExecutionFailException {


        UserCoursePrivilege entity = userCoursePrivilegeRepository.findByCourseAndUser(course, user);

        entity.setVisible(false);

        entity = userCoursePrivilegeRepository.save(entity);


        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException(String.format("No such student privileges for course %s is saved", course.getCode()));
        }

        return true;
    }


    @Override
    public List<PrivilegePojo> getAllCoursePrivileges() throws DataNotFoundException {

        List<Privilege> privileges = privilegeService.findAllByCode(getAllCoursePrivilegeCodes());
        List<PrivilegePojo> pojos = privileges
                .stream()
                .map(entity -> privilegeService.entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public List<PrivilegePojo> getAllDefaultCoursePrivilegesOfAssistant() throws DataNotFoundException {

        List<Privilege> privileges = privilegeService.findAllByCode(getAssistantDefaultPrivilegeCodes());
        List<PrivilegePojo> pojos = privileges
                .stream()
                .map(entity -> privilegeService.entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }
}

