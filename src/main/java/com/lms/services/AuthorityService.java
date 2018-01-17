package com.lms.services;

import com.lms.entities.Authority;
import com.lms.pojos.AuthorityPojo;
import org.springframework.stereotype.Service;


@Service
public class AuthorityService {

    public AuthorityPojo entityToPojo(Authority authority){
        AuthorityPojo pojo = new AuthorityPojo();
        if (authority != null){
            pojo.setAcessLevel(authority.getAcessLevel());
            pojo.setName(authority.getName());
            return pojo;
        }
        return null;
    }
}
