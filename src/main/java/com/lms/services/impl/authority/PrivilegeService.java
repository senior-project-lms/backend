package com.lms.services.impl.authority;


import com.lms.entities.authority.Privilege;
import com.lms.pojos.authority.PrivilegePojo;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {

    public PrivilegePojo entityToPojo(Privilege entity, boolean userCoursePrivileges, boolean userTypeDefaultPrivileges, boolean adminPrivileges){
        PrivilegePojo pojo = new PrivilegePojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setCode(entity.getCode());
        if (userCoursePrivileges){
            //
        }
        if (userTypeDefaultPrivileges){

        }
        if(adminPrivileges){

        }
        return pojo;
    }



}
