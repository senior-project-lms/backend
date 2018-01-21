package com.lms.services.impl.authority;


import com.lms.entities.authority.AccessPrivilege;
import com.lms.entities.authority.Privilege;
import com.lms.entities.user.User;
import com.lms.pojos.authority.AccessPrivilegePojo;
import com.lms.pojos.authority.PrivilegePojo;
import com.lms.repositories.authority.AcessPrivilegeRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.impl.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessPrivilegeService {

    @Autowired
    private AcessPrivilegeRepository acessPrivilegeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

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


    public List<Long> getPrivileges() throws Exception{
        List<Long> privileges = new ArrayList<>();
        User user = customUserDetailService.getAuthenticatedUser();
        if (user != null){
            AccessPrivilege entity = acessPrivilegeRepository.findByUser(user);
            if (entity != null){
                privileges = entity.getPrivileges().stream().map(privilege -> privilege.getCode())
                .collect(Collectors.toList());
            }
        }
        return privileges;
    }

}
