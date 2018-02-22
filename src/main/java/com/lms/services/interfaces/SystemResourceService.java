package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.pojos.SystemResourcePojo;

import java.util.List;

public interface SystemResourceService {


    SystemResourcePojo entityToPojo(SystemResource entity);

    SystemResource pojoToEntity(SystemResourcePojo pojo);

    boolean saveEntities(List<SystemResource> resources);

    boolean save(SystemResourcePojo pojo) throws ExecutionFailException;

    boolean save(List<SystemResourcePojo> pojos);

    boolean setResourceAnnouncement(String publicKey, SystemAnnouncement announcement) throws ExecutionFailException, DataNotFoundException;

    SystemResourcePojo getByName(String name) throws DataNotFoundException;

    SystemResourcePojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;


}
