package com.lms.services.impl;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.Privilege;
import com.lms.enums.ECoursePrivilege;
import com.lms.enums.EPrivilege;
import com.lms.pojos.PrivilegePojo;
import com.lms.repositories.PrivilegeRepository;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    /**
     * returns privilege entities by publicKey collection
     *
     * @param
     * @return List<Privilege>
     * @author umit.kas
     */
    @Override
    public List<Privilege> findAllByPublicKeys(List<String> publicKeys) throws DataNotFoundException {

        List<Privilege> entities = privilegeRepository.findAllByPublicKeyIn(publicKeys);

        if (entities == null) {
            throw new DataNotFoundException("No such privilege collection is found");
        }

        return entities;

    }

    /**
     * returns privilege entities by code collection
     *
     * @param
     * @return List<Privilege>
     * @author umit.kas
     */
    @Override
    public List<Privilege> findAllByCode(List<Long> codes) throws DataNotFoundException {
        List<Privilege> entities = privilegeRepository.findAllByCodeIn(codes);

        if (entities == null) {
            throw new DataNotFoundException("No such privilege collection is found");
        }

        return entities;

    }

    /**
     * returns all visible privileges
     *
     * @param
     * @return List<PrivilegePojo>
     * @author umit.kas
     */
    @Override
    public List<PrivilegePojo> getAllPrivileges() throws DataNotFoundException {

        List<Privilege> entities = privilegeRepository.findAllByVisible(true);
        if (entities == null) {
            throw new DataNotFoundException("No such privilege collection is found");
        }

        List<PrivilegePojo> pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }


    /**
     * initialize privileges, saves to db
     *
     * @author umit.kas
     */
    @Override
    public void initialize() {

        List<Privilege> privileges = new ArrayList<>();

        for (EPrivilege ePrivilege : EPrivilege.values()) {

            Privilege privilege1 = new Privilege();

            privilege1.generatePublicKey();
            privilege1.setCode(ePrivilege.CODE);
            String name = ePrivilege.toString();
            name = name.replaceAll("_", " ");
            privilege1.setName(name);
            privileges.add(privilege1);
        }

        for (ECoursePrivilege eCoursePrivilege: ECoursePrivilege.values()) {

            Privilege privilege1 = new Privilege();

            privilege1.generatePublicKey();
            privilege1.setCode(eCoursePrivilege.CODE);
            String name = eCoursePrivilege.toString();
            name = name.replaceAll("_", " ");
            privilege1.setName(name);
            privileges.add(privilege1);
        }


        privilegeRepository.save(privileges);
    }
}
