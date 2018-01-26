package com.lms.services.interfaces;

import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.pojos.SystemResourcePojo;

import java.util.List;

public interface SystemResourceService {


    SystemResourcePojo entityToPojo(SystemResource entity, boolean systemAnnouncement) throws Exception;

    SystemResource pojoToEntity(SystemResourcePojo pojo) throws Exception;

    boolean saveEntities(List<SystemResource> resources) throws Exception;

    boolean save(SystemResourcePojo pojo) throws Exception;

    boolean save(List<SystemResourcePojo> pojos) throws Exception;

    boolean setResourceAnnouncement(String publicKey, SystemAnnouncement announcement) throws Exception;

    SystemResourcePojo getByName(String name) throws Exception;

    SystemResourcePojo getByPublicKey(String publicKey) throws Exception;

    boolean delete(String publicKey) throws Exception;


}
