package com.lms.services.interfaces;

import com.lms.entities.Authority;
import com.lms.pojos.AuthorityPojo;

public interface AuthorityService {

    AuthorityPojo entityToPojo(Authority entity) throws Exception;

    Authority getAuthorityByAccessLevel(long accessLevel) throws Exception;

    Authority pojoToEntity(AuthorityPojo pojo) throws Exception;

}
