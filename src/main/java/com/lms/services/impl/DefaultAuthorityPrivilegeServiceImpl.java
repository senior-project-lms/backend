package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.entities.Privilege;
import com.lms.enums.AccessLevel;
import com.lms.enums.EPrivilege;
import com.lms.pojos.DefaultAuthorityPrivilegePojo;
import com.lms.pojos.PrivilegePojo;
import com.lms.repositories.DefaultAuthorityPrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.DefaultAuthorityPrivilegeService;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultAuthorityPrivilegeServiceImpl implements DefaultAuthorityPrivilegeService {

    @Autowired
    private DefaultAuthorityPrivilegeRepository defaultAuthorityPrivilegeRepository;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CustomUserDetailService userDetailService;


    @Override
    public DefaultAuthorityPrivilegePojo entityToPojo(DefaultAuthorityPrivilege entity) {
        DefaultAuthorityPrivilegePojo pojo = new DefaultAuthorityPrivilegePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setAuthority(authorityService.entityToPojo(entity.getAuthority()));

//        List<PrivilegePojo> privileges = entity.getPrivileges()
//                .stream()
//                .map(privilege -> privilegeService.entityToPojo(privilege))
//                .collect(Collectors.toList());

        List<String> privileges = entity.getPrivileges()
                .stream()
                .map(privilege -> privilege.getPublicKey())
                .collect(Collectors.toList());

        pojo.setPrivileges(privileges);

        return pojo;
    }

    @Override
    public DefaultAuthorityPrivilege pojoToEntity(DefaultAuthorityPrivilegePojo pojo) {
        DefaultAuthorityPrivilege entity = new DefaultAuthorityPrivilege();

        entity.setPublicKey(pojo.getPublicKey());

        entity.setAuthority(authorityService.pojoToEntity(pojo.getAuthority()));

//        List<Privilege> privileges = pojo.getPrivileges()
//                .stream()
//                .map(privilege -> privilegeService.pojoToEntity(privilege))
//                .collect(Collectors.toList());
//
//        entity.setPrivileges(privileges);

        return entity;
    }


    /**
     * save privileges for new authority type, default authorities has their own privileges initially
     *
     * @param pojo
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean save(DefaultAuthorityPrivilegePojo pojo) throws ExecutionFailException, DataNotFoundException {

        DefaultAuthorityPrivilege entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(userDetailService.getAuthenticatedUser());
        List<Privilege> privileges = privilegeService.findAllByPublicKeys(pojo.getPrivileges());
        entity.setPrivileges(privileges);
        entity = defaultAuthorityPrivilegeRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such default authority is record saved");
        }

        return true;
    }


    /**
     * update privileges of authority type
     *
     * @param pojo
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean update(DefaultAuthorityPrivilegePojo pojo) throws ExecutionFailException, DataNotFoundException {

        DefaultAuthorityPrivilege entity = pojoToEntity(pojo);
        Authority authority = authorityService.findByPublicKey(entity.getAuthority().getPublicKey());

        entity.setAuthority(authority);
        List<String> privilegePublicKeys = entity.getPrivileges()
                .stream()
                .map(privilege -> privilege.getPublicKey())
                .collect(Collectors.toList());

        List<Privilege> privileges = privilegeService.findAllByPublicKeys(privilegePublicKeys);

        entity.setPrivileges(privileges);

        entity = defaultAuthorityPrivilegeRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such authorith privilege is updated");
        }

        return true;
    }


    /**
     * returns DefaultAuthorityPrivilege pojos for all authorities, objects contains authority and its privileges
     *
     * @param
     * @return List<DefaultAuthorityPrivilegePojo>
     * @author umit.kas
     */
    @Override
    public List<DefaultAuthorityPrivilegePojo> getDefaultAuthorityPrivileges() throws DataNotFoundException {

        List<DefaultAuthorityPrivilege> entities = defaultAuthorityPrivilegeRepository.findAllByVisible(true);
        if (entities == null) {

            throw new DataNotFoundException("No such privilege collection is found");
        }

        List<DefaultAuthorityPrivilegePojo> pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());
        return pojos;
    }


    /**
     * returns DefaultAuthorityPrivilege entity by authority
     *
     * @param authority
     * @return DefaultAuthorityPrivilege
     * @author umit.kas
     */
    @Override
    public DefaultAuthorityPrivilege findByAuthority(Authority authority) throws DataNotFoundException {
        DefaultAuthorityPrivilege entity = defaultAuthorityPrivilegeRepository.findByAuthority(authority);
        if (entity == null) {
            throw new DataNotFoundException("No such privilege collection is found");
        }
        return entity;
    }


    /**
     * initialize privileges for default authorities
     *
     * @author umit.kas
     */
    @Override
    public void initialize() throws DataNotFoundException {
        initializeSuperAdmin();
        initializeAdmin();
        initializeLecturer();
        initializeStudent();
        initializeAssistant();

    }

    /**
     * initialize privileges for super admin authority
     *
     *
     * @author umit.kas
     */
    private void initializeSuperAdmin() throws DataNotFoundException {
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = new DefaultAuthorityPrivilege();

        Authority authority = authorityService.findByCode(AccessLevel.SUPER_ADMIN.CODE);
        List<Privilege> privileges = privilegeService.findAllByCode(getDefaultSuperAdminPrivileges());

        defaultAuthorityPrivilege.setAuthority(authority);
        defaultAuthorityPrivilege.setPrivileges(privileges);
        defaultAuthorityPrivilege.generatePublicKey();

        defaultAuthorityPrivilegeRepository.save(defaultAuthorityPrivilege);
    }

    /**
     * initialize privileges for  admin authority
     *
     *
     * @author umit.kas
     */
    private void initializeAdmin() throws DataNotFoundException {
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = new DefaultAuthorityPrivilege();

        Authority studentAuthority = authorityService.findByCode(AccessLevel.ADMIN.CODE);
        List<Privilege> studentPrivileges = privilegeService.findAllByCode(getDefaultAdminPrivileges());

        defaultAuthorityPrivilege.setAuthority(studentAuthority);
        defaultAuthorityPrivilege.setPrivileges(studentPrivileges);
        defaultAuthorityPrivilege.generatePublicKey();

        defaultAuthorityPrivilegeRepository.save(defaultAuthorityPrivilege);
    }


    /**
     * initialize privileges for lecturer authority
     *
     *
     * @author umit.kas
     */
    private void initializeLecturer() throws DataNotFoundException {
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = new DefaultAuthorityPrivilege();

        Authority studentAuthority = authorityService.findByCode(AccessLevel.LECTURER.CODE);
        List<Privilege> studentPrivileges = privilegeService.findAllByCode(getDefaultLecturerPrivileges());

        defaultAuthorityPrivilege.setAuthority(studentAuthority);
        defaultAuthorityPrivilege.setPrivileges(studentPrivileges);
        defaultAuthorityPrivilege.generatePublicKey();

        defaultAuthorityPrivilegeRepository.save(defaultAuthorityPrivilege);
    }

    /**
     * initialize privileges for assistant authority
     *
     * @author umit.kas
     */
    private void initializeAssistant() throws DataNotFoundException {
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = new DefaultAuthorityPrivilege();

        Authority studentAuthority = authorityService.findByCode(AccessLevel.ASSISTANT.CODE);
        List<Privilege> studentPrivileges = privilegeService.findAllByCode(getDefaultAssistantPrivileges());

        defaultAuthorityPrivilege.setAuthority(studentAuthority);
        defaultAuthorityPrivilege.setPrivileges(studentPrivileges);
        defaultAuthorityPrivilege.generatePublicKey();

        defaultAuthorityPrivilegeRepository.save(defaultAuthorityPrivilege);
    }

    /**
     * initialize privileges for student authority
     *
     *
     * @author umit.kas
     */
    private void initializeStudent() throws DataNotFoundException {
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = new DefaultAuthorityPrivilege();

        Authority studentAuthority = authorityService.findByCode(AccessLevel.STUDENT.CODE);
        List<Privilege> studentPrivileges = privilegeService.findAllByCode(getDefaultStudentPrivileges());

        defaultAuthorityPrivilege.setAuthority(studentAuthority);
        defaultAuthorityPrivilege.setPrivileges(studentPrivileges);
        defaultAuthorityPrivilege.generatePublicKey();

        defaultAuthorityPrivilegeRepository.save(defaultAuthorityPrivilege);
    }


    // default authorities

    private List<Long> getDefaultSuperAdminPrivileges() {
        return Arrays.asList(
                // SYSTEM ANNOUNCEMENT
                EPrivilege.SAVE_SYSTEM_ANNOUNCEMENT.CODE,
                EPrivilege.READ_SYSTEM_ANNOUNCEMENT.CODE,
                EPrivilege.DELETE_SYSTEM_ANNOUNCEMENT.CODE,
                EPrivilege.UPDATE_SYSTEM_ANNOUNCEMENT.CODE,
                EPrivilege.UPLOAD_SYSTEM_ANNOUNCEMENT_FILE.CODE,
                EPrivilege.DELETE_SYSTEM_ANNOUNCEMENT_FILE.CODE,

                // USER
                EPrivilege.SAVE_USER.CODE,
                EPrivilege.READ_ALL_USERS.CODE,
                EPrivilege.READ_USER.CODE,
                EPrivilege.DELETE_USER.CODE,
                EPrivilege.UPDATE_USER.CODE,

                // COURSE
                EPrivilege.SAVE_COURSE.CODE,
                EPrivilege.READ_ALL_COURSES.CODE,
                EPrivilege.DELETE_COURSE.CODE,
                EPrivilege.UPDATE_COURSE.CODE,
                EPrivilege.READ_COURSES_BY_VISIBILITY.CODE,
                EPrivilege.READ_COURSE_STATUSES.CODE,
                EPrivilege.UPDATE_COURSE_VISIBILITY.CODE,
                EPrivilege.READ_REGISTERED_STUDENTS.CODE,

                // AUTHORITY
                EPrivilege.SAVE_AUTHORITY.CODE,
                EPrivilege.READ_ALL_AUTHORITIES.CODE,
                EPrivilege.DELETE_AUTHORITY.CODE,
                EPrivilege.UPDATE_AUTHORITY.CODE,

                // PRIVILEGES
                EPrivilege.READ_ALL_PRIVILEGES.CODE,
                // DEFAULT AUTHORITY
                EPrivilege.READ_DEFAULT_AUTHORITIES_AND_PRIVILEGES.CODE,
                EPrivilege.UPDATE_DEFAULT_AUTHORITY.CODE,
                EPrivilege.SAVE_DEFAULT_AUTHORITY.CODE,
                EPrivilege.DELETE_DEFAULT_AUTHORITY.CODE,

                EPrivilege.APPROVE_ENROLLMENT_REQUEST.CODE,
                EPrivilege.READ_ENROLLMENT_REQUESTS.CODE,
                EPrivilege.CANCEL_ENROLLMENT_REQUEST.CODE,
                EPrivilege.REJECT_ENROLLMENT_REQUEST.CODE,

                EPrivilege.GLOBAL_ACCESS.CODE

//                EPrivilege.UPDATE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,


        );
    }

    private List<Long> getDefaultAdminPrivileges() {
        return Arrays.asList(

                // SYSTEM ANNOUNCEMENT
                EPrivilege.SAVE_SYSTEM_ANNOUNCEMENT.CODE,
                EPrivilege.READ_SYSTEM_ANNOUNCEMENT.CODE,
//                EPrivilege.DELETE_SYSTEM_ANNOUNCEMENT.CODE,
//                EPrivilege.UPDATE_SYSTEM_ANNOUNCEMENT.CODE,
//                EPrivilege.UPLOAD_SYSTEM_ANNOUNCEMENT_FILE.CODE,
//                EPrivilege.DELETE_SYSTEM_ANNOUNCEMENT_FILE.CODE,

                // USER
//                EPrivilege.SAVE_USER.CODE,
                EPrivilege.READ_ALL_USERS.CODE,
                EPrivilege.READ_USER.CODE,
                //EPrivilege.DELETE_USER.CODE,
                //EPrivilege.UPDATE_USER.CODE,

                // COURSE
//                EPrivilege.SAVE_COURSE.CODE,
                EPrivilege.READ_ALL_COURSES.CODE,
//                EPrivilege.DELETE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,
                EPrivilege.READ_COURSES_BY_VISIBILITY.CODE,
                EPrivilege.READ_COURSE_STATUSES.CODE,
                //EPrivilege.UPDATE_COURSE_VISIBILITY.CODE,
                EPrivilege.READ_REGISTERED_STUDENTS.CODE,

                // AUTHORITY
//                EPrivilege.SAVE_AUTHORITY.CODE,
//                EPrivilege.READ_ALL_AUTHORITIES.CODE,
//                EPrivilege.DELETE_AUTHORITY.CODE,
//                EPrivilege.UPDATE_AUTHORITY.CODE,

                EPrivilege.READ_DEFAULT_AUTHORITIES_AND_PRIVILEGES.CODE,
//                EPrivilege.UPDATE_DEFAULT_AUTHORITY.CODE,
//                EPrivilege.SAVE_DEFAULT_AUTHORITY.CODE,
//                EPrivilege.DELETE_DEFAULT_AUTHORITY.CODE,

                // DEFAULT AUTHORITY
                EPrivilege.APPROVE_ENROLLMENT_REQUEST.CODE,
                EPrivilege.READ_ENROLLMENT_REQUESTS.CODE,

                // PRIVILEGES

                EPrivilege.READ_ALL_PRIVILEGES.CODE,

                EPrivilege.GLOBAL_ACCESS.CODE,
                EPrivilege.CANCEL_ENROLLMENT_REQUEST.CODE,
                EPrivilege.REJECT_ENROLLMENT_REQUEST.CODE


//                EPrivilege.UPDATE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,
//                EPrivilege.UPDATE_COURSE.CODE,


        );
    }

    private List<Long> getDefaultLecturerPrivileges() {
        return Arrays.asList(
                // SYSTEM ANNOUNCEMENT
                EPrivilege.READ_SYSTEM_ANNOUNCEMENT.CODE,

                // USER


                // COURSE

                EPrivilege.READ_REGISTERED_STUDENTS.CODE,
                EPrivilege.READ_AUTHENTICATED_COURSES.CODE,
                EPrivilege.ACCESS_COURSES_PAGE.CODE,

                // AUTHORITY

                // DEFAULT AUTHORITY

                // ENROLLMENT
                EPrivilege.APPROVE_ENROLLMENT_REQUEST.CODE,
                EPrivilege.READ_ENROLLMENT_REQUESTS.CODE,
                EPrivilege.GLOBAL_ACCESS.CODE,
                EPrivilege.CANCEL_ENROLLMENT_REQUEST.CODE,
                EPrivilege.CANCEL_ENROLLMENT_REQUEST.CODE,
                EPrivilege.REJECT_ENROLLMENT_REQUEST.CODE



        );
    }


    private List<Long> getDefaultStudentPrivileges() {
        return Arrays.asList(
                // SYSTEM ANNOUNCEMENT
                EPrivilege.READ_SYSTEM_ANNOUNCEMENT.CODE,

                // USER


                // COURSE
                EPrivilege.READ_NOT_REGISTERED_COURSES.CODE,
                EPrivilege.READ_REGISTERED_COURSES.CODE,
                EPrivilege.READ_AUTHENTICATED_COURSES.CODE,
                EPrivilege.ACCESS_COURSES_PAGE.CODE,

                // AUTHORITY

                // DEFAULT AUTHORITY

                // ENROLLMENT
                EPrivilege.ENROLL_COURSE.CODE,

                EPrivilege.GLOBAL_ACCESS.CODE,
                EPrivilege.READ_REQUESTED_ENROLLMENT_REQUESTS.CODE,
                EPrivilege.CANCEL_ENROLLMENT_REQUEST.CODE


        );
    }

    private List<Long> getDefaultAssistantPrivileges() {
        return Arrays.asList(
                // SYSTEM ANNOUNCEMENT
                EPrivilege.READ_SYSTEM_ANNOUNCEMENT.CODE,

                // USER


                // COURSE

                EPrivilege.READ_REGISTERED_STUDENTS.CODE,
                EPrivilege.READ_AUTHENTICATED_COURSES.CODE,
                EPrivilege.ACCESS_COURSES_PAGE.CODE,

                // AUTHORITY

                // DEFAULT AUTHORITY

                // ENROLLMENT
                // ENROLLMENT
                EPrivilege.ENROLL_COURSE.CODE,

                EPrivilege.APPROVE_ENROLLMENT_REQUEST.CODE,
                EPrivilege.READ_ENROLLMENT_REQUESTS.CODE,
                EPrivilege.GLOBAL_ACCESS.CODE,
                EPrivilege.READ_REQUESTED_ENROLLMENT_REQUESTS.CODE,
                EPrivilege.CANCEL_ENROLLMENT_REQUEST.CODE,
                EPrivilege.REJECT_ENROLLMENT_REQUEST.CODE
        );
    }


}
