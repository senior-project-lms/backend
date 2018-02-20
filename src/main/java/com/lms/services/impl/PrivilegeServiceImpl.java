package com.lms.services.impl;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.Privilege;
import com.lms.enums.EPrivilege;
import com.lms.pojos.PrivilegePojo;
import com.lms.repositories.PrivilegeRepository;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{

    @Autowired
    private PrivilegeRepository privilegeRepository;


    /**
     * Converts Authority entity to AuthorityPojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @return PrivilegePojo
     *
     */
    public PrivilegePojo entityToPojo(Privilege entity) {
        PrivilegePojo pojo = new PrivilegePojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setCode(entity.getCode());
        return pojo;
    }


    @Override
    public Privilege pojoToEntity(PrivilegePojo pojo) {
        Privilege entity = new Privilege();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setCode(pojo.getCode());
        entity.setName(pojo.getName());
        return entity;
    }

    @Override
    public List<Privilege> findAllByPublicKeys(List<String> publicKeys) throws DataNotFoundException {

        List<Privilege> privileges = privilegeRepository.findAllByPublicKeyIn(publicKeys);

        if (privileges == null) {
            throw new DataNotFoundException("No such privilege collection is found");
        }

        return privileges;

    }


    @Override
    public List<Privilege> findAllByCode(List<Long> codes) throws DataNotFoundException {
        List<Privilege> privileges = privilegeRepository.findAllByCodeIn(codes);

        if (privileges == null) {
            throw new DataNotFoundException("No such privilege collection is found");
        }

        return privileges;

    }

    @Override
    public void initialize() {

        List<Privilege> privileges = new ArrayList<>();

        for (EPrivilege EPrivilege : EPrivilege.values()) {

            Privilege privilege1 = new Privilege();

            privilege1.generatePublicKey();
            privilege1.setCode(EPrivilege.CODE);
            privilege1.setName(EPrivilege.toString());
            privileges.add(privilege1);
        }

        privilegeRepository.save(privileges);
    }
}
