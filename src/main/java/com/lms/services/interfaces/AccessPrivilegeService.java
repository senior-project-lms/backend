package com.lms.services.interfaces;

import com.lms.entities.AccessPrivilege;
import com.lms.pojos.AccessPrivilegePojo;

import java.util.List;

public interface AccessPrivilegeService {

    AccessPrivilegePojo entityToPojo(AccessPrivilege entity) throws Exception;

    AccessPrivilege pojoToEntity(AccessPrivilegePojo pojo) throws Exception;

    List<Long> getAuthenticatedUserAccessPrivileges() throws Exception;
}
