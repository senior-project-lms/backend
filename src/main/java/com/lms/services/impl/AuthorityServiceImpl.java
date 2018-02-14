package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.Authority;
import com.lms.enums.ExceptionType;
import com.lms.pojos.AuthorityPojo;
import com.lms.repositories.AuthorityRepository;
import com.lms.services.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    /**
     * Converts Authority entity to AuthorityPojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param entity
     * @return AuthorityPojo
     * @author umit.kas
     */
    @Override
    public AuthorityPojo entityToPojo(Authority entity) {
        AuthorityPojo pojo = new AuthorityPojo();
        pojo.setAccessLevel(entity.getAccessLevel());
        pojo.setName(entity.getName());
        pojo.setPublicKey(entity.getPublicKey());
        return pojo;
    }

    @Override
    public Authority findByPublicKey(String publicKey) throws DataNotFoundException {
        Authority entity = authorityRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException(String.format("Authority is not found by accessLevel: %s", publicKey));
        }
        return entity;
    }

    @Override
    public Authority pojoToEntity(AuthorityPojo pojo) {
        Authority entity = new Authority();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setAccessLevel(pojo.getAccessLevel());
        entity.setName(pojo.getName());
        return entity;
    }

    @Override
    public List<AuthorityPojo> getAuthorities() {
        List<AuthorityPojo> pojos = new ArrayList<>();
        for (Authority entity : authorityRepository.findAllByVisible(true)) {
            pojos.add(entityToPojo(entity));
        }
        return pojos;
    }


}
