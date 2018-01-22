package com.lms.services.impl.authority;

import com.lms.entities.authority.Authority;
import com.lms.pojos.authority.AuthorityPojo;
import com.lms.services.interfaces.AuthorityService;
import org.springframework.stereotype.Service;


@Service
public class AuthorityServiceImpl implements AuthorityService{


    /**
     * Converts Authority entity to AuthorityPojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @return AuthorityPojo
     *
     */
    @Override
    public AuthorityPojo entityToPojo(Authority entity) throws Exception{
        AuthorityPojo pojo = new AuthorityPojo();
        if (entity != null){
            pojo.setAccessLevel(entity.getAccessLevel());
            pojo.setName(entity.getName());
            return pojo;
        }
        return pojo;
    }
}
