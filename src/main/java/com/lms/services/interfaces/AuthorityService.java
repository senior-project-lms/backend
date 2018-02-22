package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.Authority;
import com.lms.pojos.AuthorityPojo;

import java.util.List;

public interface AuthorityService {

    AuthorityPojo entityToPojo(Authority entity);

    Authority pojoToEntity(AuthorityPojo pojo);

    List<AuthorityPojo> getAuthorities();

    Authority findByPublicKey(String publicKey) throws DataNotFoundException;

    Authority findByCode(long code) throws DataNotFoundException;

    List<Authority> findAllByPublicKey(List<String> publicKeys) throws DataNotFoundException;

    void initialize();
}
