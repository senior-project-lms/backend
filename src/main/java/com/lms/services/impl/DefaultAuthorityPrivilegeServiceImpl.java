package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.entities.Privilege;
import com.lms.enums.ExceptionType;
import com.lms.pojos.DefaultAuthorityPrivilegePojo;
import com.lms.pojos.PrivilegePojo;
import com.lms.repositories.DefaultAuthorityPrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.DefaultAuthorityPrivilegeService;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<PrivilegePojo> privileges = entity.getPrivileges()
                .stream()
                .map(privilege -> privilegeService.entityToPojo(privilege))
                .collect(Collectors.toList());

        pojo.setPrivileges(privileges);

        return pojo;
    }

    @Override
    public DefaultAuthorityPrivilege pojoToEntity(DefaultAuthorityPrivilegePojo pojo) {
        DefaultAuthorityPrivilege entity = new DefaultAuthorityPrivilege();

        entity.setPublicKey(pojo.getPublicKey());

        entity.setAuthority(authorityService.pojoToEntity(pojo.getAuthority()));

        List<Privilege> privileges = pojo.getPrivileges()
                .stream()
                .map(privilege -> privilegeService.pojoToEntity(privilege))
                .collect(Collectors.toList());

        entity.setPrivileges(privileges);

        return entity;
    }

    @Override
    public boolean save(DefaultAuthorityPrivilegePojo pojo) throws ExecutionFailException {

        DefaultAuthorityPrivilege entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(userDetailService.getAuthenticatedUser());
        entity = defaultAuthorityPrivilegeRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such default authority is record saved");
        }

        return true;
    }

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

    @Override
    public List<DefaultAuthorityPrivilegePojo> getDefaultAuthorityPrivileges() throws DataNotFoundException {

        List<DefaultAuthorityPrivilege> entities = defaultAuthorityPrivilegeRepository.findAllByVisible(true);
        if (entities == null) {

            throw new DataNotFoundException("No such privilege collection is found");
        }

        List<DefaultAuthorityPrivilegePojo> pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());

        return pojos;
    }


}
