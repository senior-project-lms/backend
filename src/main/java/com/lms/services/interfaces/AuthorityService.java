package com.lms.services.interfaces;

import com.lms.entities.authority.Authority;
import com.lms.pojos.authority.AuthorityPojo;

public interface AuthorityService {

    AuthorityPojo entityToPojo(Authority entity) throws Exception;
}
