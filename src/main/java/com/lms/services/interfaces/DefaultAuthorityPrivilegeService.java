package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.pojos.DefaultAuthorityPrivilegePojo;


import java.util.List;
import java.util.jar.JarEntry;

public interface DefaultAuthorityPrivilegeService {

    DefaultAuthorityPrivilege findByAuthority(Authority authority) throws DataNotFoundException;

    DefaultAuthorityPrivilegePojo entityToPojo(DefaultAuthorityPrivilege entity);

    DefaultAuthorityPrivilege pojoToEntity(DefaultAuthorityPrivilegePojo pojo);


    boolean save(DefaultAuthorityPrivilegePojo pojo) throws ExecutionFailException, DataNotFoundException;

    boolean update(DefaultAuthorityPrivilegePojo pojo) throws ExecutionFailException, DataNotFoundException;

    List<DefaultAuthorityPrivilegePojo> getDefaultAuthorityPrivileges() throws DataNotFoundException;


    void initialize() throws DataNotFoundException;


}
