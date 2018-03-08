package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.entities.Privilege;
import com.lms.enums.AccessLevel;
import com.lms.enums.ECoursePrivilege;
import com.lms.enums.commonUserPrivileges.*;
import com.lms.pojos.DefaultAuthorityPrivilegePojo;
import com.lms.repositories.DefaultAuthorityPrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.DefaultAuthorityPrivilegeService;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Authority authority = authorityService.findByCode(AccessLevel.ADMIN.CODE);
        List<Privilege> privileges = privilegeService.findAllByCode(getDefaultAdminPrivileges());

        defaultAuthorityPrivilege.setAuthority(authority);
        defaultAuthorityPrivilege.setPrivileges(privileges);
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

        Authority authority = authorityService.findByCode(AccessLevel.LECTURER.CODE);
        List<Privilege> privileges = privilegeService.findAllByCode(getDefaultLecturerPrivileges());

        defaultAuthorityPrivilege.setAuthority(authority);
        defaultAuthorityPrivilege.setPrivileges(privileges);
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

        Authority authority = authorityService.findByCode(AccessLevel.ASSISTANT.CODE);
        List<Privilege> privileges = privilegeService.findAllByCode(getDefaultAssistantPrivileges());

        defaultAuthorityPrivilege.setAuthority(authority);
        defaultAuthorityPrivilege.setPrivileges(privileges);
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

        Authority authority = authorityService.findByCode(AccessLevel.STUDENT.CODE);
        List<Privilege> privileges = privilegeService.findAllByCode(getDefaultStudentPrivileges());

        defaultAuthorityPrivilege.setAuthority(authority);
        defaultAuthorityPrivilege.setPrivileges(privileges);
        defaultAuthorityPrivilege.generatePublicKey();

        defaultAuthorityPrivilegeRepository.save(defaultAuthorityPrivilege);
    }


    // default authorities


    private List<Long> getDefaultSuperAdminPrivileges() {
        List<Long> privileges = new ArrayList<>();

        for (CommonSuperAdminPrivilege commonSuperAdminPrivilege : CommonSuperAdminPrivilege.values()){
            privileges.add(commonSuperAdminPrivilege.CODE);
        }

        return privileges;

    }


    private List<Long> getDefaultAdminPrivileges() {

        List<Long> privileges = new ArrayList<>();

        for (CommonAdminPrivilege commonAdminPrivilege : CommonAdminPrivilege.values()){
            privileges.add(commonAdminPrivilege.CODE);
        }

        return privileges;


    }

    private List<Long> getDefaultLecturerPrivileges() {
        List<Long> privileges = new ArrayList<>();

        for (CommonLecturerPrivilege commonLecturerPrivilege : CommonLecturerPrivilege.values()){
            privileges.add(commonLecturerPrivilege.CODE);
        }

        privileges.add(ECoursePrivilege.READ_AUTHENTICATED_COURSES.CODE);
        privileges.add(ECoursePrivilege.PAGE_COURSE.CODE);

        return privileges;

    }


    private List<Long> getDefaultStudentPrivileges() {
        List<Long> privileges = new ArrayList<>();

        for (CommonStudentPrivilege commonStudentPrivilege : CommonStudentPrivilege.values()){
            privileges.add(commonStudentPrivilege.CODE);
        }

        privileges.add(ECoursePrivilege.ENROLL_COURSE.CODE);
        privileges.add(ECoursePrivilege.CANCEL_ENROLLMENT_REQUEST.CODE);
        privileges.add(ECoursePrivilege.READ_REQUESTED_ENROLLMENT_REQUESTS.CODE);
        privileges.add(ECoursePrivilege.READ_REGISTERED_COURSES.CODE);
        privileges.add(ECoursePrivilege.READ_AUTHENTICATED_COURSES.CODE);
        privileges.add(ECoursePrivilege.READ_NOT_REGISTERED_COURSES.CODE);
        privileges.add(ECoursePrivilege.PAGE_COURSE.CODE);

        return privileges;

    }

    private List<Long> getDefaultAssistantPrivileges() {
        List<Long> privileges = new ArrayList<>();

        for (CommonAssistantPrivilege commonAssistantPrivilege : CommonAssistantPrivilege.values()){
            privileges.add(commonAssistantPrivilege.CODE);
        }

        privileges.add(ECoursePrivilege.ENROLL_COURSE.CODE);
        privileges.add(ECoursePrivilege.CANCEL_ENROLLMENT_REQUEST.CODE);
        privileges.add(ECoursePrivilege.READ_REQUESTED_ENROLLMENT_REQUESTS.CODE);
        privileges.add(ECoursePrivilege.READ_REGISTERED_COURSES.CODE);
        privileges.add(ECoursePrivilege.READ_AUTHENTICATED_COURSES.CODE);
        privileges.add(ECoursePrivilege.READ_NOT_REGISTERED_COURSES.CODE);
        privileges.add(ECoursePrivilege.PAGE_COURSE.CODE);

        return privileges;

    }


}
