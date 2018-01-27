package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.AccessPrivilege;
import com.lms.pojos.AccessPrivilegePojo;

import java.util.List;

public interface AccessPrivilegeService {

    AccessPrivilegePojo entityToPojo(AccessPrivilege entity);

    AccessPrivilege pojoToEntity(AccessPrivilegePojo pojo);

    List<Long> getAuthenticatedUserAccessPrivileges() throws ServiceException;
}
