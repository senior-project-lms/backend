package com.lms.services.interfaces;

import com.lms.entities.global.SystemResource;
import com.lms.pojos.global.SystemResourcePojo;

import java.util.List;

public interface SystemResourceService {


    SystemResourcePojo entityToPojo(SystemResource entity, boolean systemAnnouncement) throws Exception;

    SystemResource pojoToEntity(SystemResourcePojo pojo) throws Exception;

    boolean saveEntities(List<SystemResource> resources) throws Exception;

    boolean save(SystemResourcePojo pojo) throws Exception;

    boolean save(List<SystemResourcePojo> pojos) throws Exception;

}
