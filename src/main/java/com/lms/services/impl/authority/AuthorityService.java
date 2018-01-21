package com.lms.services.impl.authority;

import com.lms.entities.authority.Authority;
import com.lms.pojos.authority.AuthorityPojo;
import org.springframework.stereotype.Service;


@Service
public class AuthorityService {

    public AuthorityPojo entityToPojo(Authority authority){
        AuthorityPojo pojo = new AuthorityPojo();
        if (authority != null){
            pojo.setAccessLevel(authority.getAccessLevel());
            pojo.setName(authority.getName());
            return pojo;
        }
        return null;
    }
}
