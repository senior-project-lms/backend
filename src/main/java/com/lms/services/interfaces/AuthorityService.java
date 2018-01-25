package com.lms.services.interfaces;

import com.lms.entities.authority.Authority;
import com.lms.pojos.authority.AuthorityPojo;

public interface AuthorityService {

    AuthorityPojo entityToPojo(Authority entity) throws Exception;

    Authority getAuthorityByAccessLevel(long accessLevel) throws Exception;

    Authority pojoToEntity(AuthorityPojo pojo) throws Exception;

}
