package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.pojos.SystemResourcePojo;

import java.util.List;

public interface SystemResourceService {


    SystemResourcePojo entityToPojo(SystemResource entity);

    SystemResource pojoToEntity(SystemResourcePojo pojo);

    boolean saveEntities(List<SystemResource> resources) throws ServiceException;

    boolean save(SystemResourcePojo pojo) throws ServiceException;

    boolean save(List<SystemResourcePojo> pojos) throws ServiceException;

    boolean setResourceAnnouncement(String publicKey, SystemAnnouncement announcement) throws ServiceException;

    SystemResourcePojo getByName(String name) throws ServiceException;

    SystemResourcePojo getByPublicKey(String publicKey) throws ServiceException;

    boolean delete(String publicKey) throws ServiceException;


}
