package com.lms.services.impl.authority;


import com.lms.entities.authority.Privilege;
import com.lms.pojos.authority.PrivilegePojo;
import com.lms.services.interfaces.PrivilegeService;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{



    /**
     * Converts Authority entity to AuthorityPojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity, userCoursePrivileges, userTypeDefaultPrivileges, adminPrivileges
     * @return PrivilegePojo
     *
     */
    public PrivilegePojo entityToPojo(Privilege entity, boolean userCoursePrivileges, boolean userTypeDefaultPrivileges, boolean adminPrivileges) throws Exception{
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


    @Override
    public Privilege pojoToEntity(PrivilegePojo pojo) throws Exception {
        return null;
    }
}
