package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.Privilege;
import com.lms.pojos.PrivilegePojo;

import java.util.List;

public interface PrivilegeService {

    PrivilegePojo entityToPojo(Privilege entity);

    Privilege pojoToEntity(PrivilegePojo pojo);

    List<Privilege> findAllByPublicKeys(List<String> publicKeys) throws DataNotFoundException;

    List<Privilege> findAllByCode(List<Long> codes) throws DataNotFoundException;

    void initialize();

}

