package com.lms.services.impl;

import com.lms.entities.Authority;
import com.lms.pojos.AuthorityPojo;
import com.lms.repositories.AuthorityRepository;
import com.lms.services.interfaces.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public AuthorityPojo entityToPojo(Authority entity) throws Exception {
        AuthorityPojo pojo = new AuthorityPojo();
        if (entity != null) {
            pojo.setAccessLevel(entity.getAccessLevel());
            pojo.setName(entity.getName());
            return pojo;
        }
        return pojo;
    }

    @Override
    public Authority getAuthorityByAccessLevel(long accessLevel) throws Exception {
        Authority entity = authorityRepository.findByAccessLevel(accessLevel);

        return entity;
    }

    @Override
    public Authority pojoToEntity(AuthorityPojo pojo) throws Exception {
        Authority entity = new Authority();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setName(pojo.getName());
        entity.setAccessLevel(pojo.getAccessLevel());
        return entity;
    }
}
