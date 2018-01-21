package com.lms.services.impl.authority;

import com.lms.entities.authority.Authority;
import com.lms.pojos.authority.AuthorityPojo;
import org.springframework.stereotype.Service;


@Service
public class AuthorityService {


    /**
     * Converts Authority entity to AuthorityPojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @return AuthorityPojo
     *
     */
    public AuthorityPojo entityToPojo(Authority entity){
        AuthorityPojo pojo = new AuthorityPojo();
        if (entity != null){
            pojo.setAccessLevel(entity.getAccessLevel());
            pojo.setName(entity.getName());
            return pojo;
        }
        return pojo;
    }
}
