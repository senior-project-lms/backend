package com.lms.services.interfaces;

import com.lms.entities.Privilege;
import com.lms.pojos.PrivilegePojo;

public interface PrivilegeService {

    PrivilegePojo entityToPojo(Privilege entity, boolean userCoursePrivileges, boolean userTypeDefaultPrivileges, boolean adminPrivileges) throws Exception;

    Privilege pojoToEntity(PrivilegePojo pojo) throws Exception;

}

