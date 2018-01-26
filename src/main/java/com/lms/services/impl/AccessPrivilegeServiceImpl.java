package com.lms.services.impl;


import com.lms.entities.AccessPrivilege;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.pojos.AccessPrivilegePojo;
import com.lms.pojos.PrivilegePojo;
import com.lms.repositories.AccessPrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AccessPrivilegeService;
import com.lms.services.interfaces.PrivilegeService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessPrivilegeServiceImpl implements AccessPrivilegeService{

    @Autowired
    private AccessPrivilegeRepository accessPrivilegeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private CustomUserDetailService customUserDetailService;




    @Override
    public AccessPrivilege pojoToEntity(AccessPrivilegePojo pojo) throws Exception {
        return null;
    }

    /**
     * Converts AccessPrivilege entity to AccessPrivilegePojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @return AccessPrivilegePojo
     *
     */

    @Override
    public AccessPrivilegePojo entityToPojo(AccessPrivilege entity) throws Exception{
        AccessPrivilegePojo pojo = new AccessPrivilegePojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setUser(userService.entityToPojo(entity.getUser(),false, false,false));

        List<PrivilegePojo> privilegePojos = new ArrayList<>();

        for (Privilege privilege : entity.getPrivileges()){
            privilegePojos.add(privilegeService.entityToPojo(privilege,false,false, false));
        }
        pojo.setPrivileges(privilegePojos);

        return pojo;
    }



    /**
     *
     * Returns privileges code list
     * according to authenticated user, finds the privileges of authenticated user, and returns it
     *
     * @author umit.kas
     * @param
     * @return List<Long>
     *
     */
    @Override
    public List<Long> getAuthenticatedUserAccessPrivileges() throws Exception{
        List<Long> privileges = new ArrayList<>();
        User user = customUserDetailService.getAuthenticatedUser();
        if (user != null){
            AccessPrivilege entity = accessPrivilegeRepository.findByUser(user);
            if (entity != null){
                privileges = entity.getPrivileges().stream().map(privilege -> privilege.getCode())
                .collect(Collectors.toList());
            }
        }
        return privileges;
    }

}
