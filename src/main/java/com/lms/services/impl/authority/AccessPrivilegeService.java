package com.lms.services.impl.authority;


import com.lms.entities.authority.AccessPrivilege;
import com.lms.entities.authority.Privilege;
import com.lms.entities.user.User;
import com.lms.pojos.authority.AccessPrivilegePojo;
import com.lms.pojos.authority.PrivilegePojo;
import com.lms.repositories.authority.AccessPrivilegeRepository;
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
    private AccessPrivilegeRepository accessPrivilegeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    /**
     * Converts AccessPrivilege entity to AccessPrivilegePojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @return AccessPrivilegePojo
     *
     */
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
    public List<Long> getPrivileges() throws Exception{
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
