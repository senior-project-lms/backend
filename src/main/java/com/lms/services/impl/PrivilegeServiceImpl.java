package com.lms.services.impl;


import com.lms.entities.Privilege;
import com.lms.pojos.PrivilegePojo;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{



    /**
     * Converts Authority entity to AuthorityPojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @param userCoursePrivileges
     * @param userTypeDefaultPrivileges
     * @param accessPrivileges
     * @return PrivilegePojo
     *
     */
    public PrivilegePojo entityToPojo(Privilege entity, boolean userCoursePrivileges, boolean userTypeDefaultPrivileges, boolean accessPrivileges){
        PrivilegePojo pojo = new PrivilegePojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setCode(entity.getCode());
        if (userCoursePrivileges){

        }
        if (userTypeDefaultPrivileges){

        }
        if(accessPrivileges){

        }
        return pojo;
    }


    @Override
    public Privilege pojoToEntity(PrivilegePojo pojo) {
        return null;
    }
}
