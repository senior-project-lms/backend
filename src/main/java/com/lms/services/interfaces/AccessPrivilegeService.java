package com.lms.services.interfaces;

import com.lms.entities.authority.AccessPrivilege;
import com.lms.pojos.authority.AccessPrivilegePojo;

import java.util.List;

public interface AccessPrivilegeService {

    AccessPrivilegePojo entityToPojo(AccessPrivilege entity) throws Exception;

    AccessPrivilege pojoToEntity(AccessPrivilegePojo pojo) throws Exception;

    List<Long> getAuthenticatedUserAccessPrivileges() throws Exception;
}
