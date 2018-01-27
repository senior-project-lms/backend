package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.Authority;
import com.lms.pojos.AuthorityPojo;

public interface AuthorityService {

    AuthorityPojo entityToPojo(Authority entity);

    Authority getAuthorityByPublicKey(String publicKey) throws ServiceException;

    Authority pojoToEntity(AuthorityPojo pojo);

}
