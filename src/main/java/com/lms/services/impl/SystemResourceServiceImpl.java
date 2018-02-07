package com.lms.services.impl;

import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ServiceException;
import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.entities.User;
import com.lms.enums.ExceptionType;
import com.lms.pojos.SystemResourcePojo;
import com.lms.repositories.SystemResourceRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.SystemAnnouncementService;
import com.lms.services.interfaces.SystemResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemResourceServiceImpl implements SystemResourceService{


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private SystemAnnouncementService systemAnnouncementService;

    @Autowired
    private SystemResourceRepository systemResourceRepository;

    // file is not added inside the pojo and entity


    /**
     * Converts SystemResource entity to SystemResourcePojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity
     * @return SystemResourcePojo
     */
    @Override
    public SystemResourcePojo entityToPojo(SystemResource entity) {

        SystemResourcePojo pojo = new SystemResourcePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        //pojo.setPath(entity.getPath());
        pojo.setUrl(entity.getUrl());
        pojo.setType(entity.getType());
        pojo.setOriginalFileName(entity.getOriginalFileName());
        return pojo;
    }


    /**
     * Converts SystemResourcePojo to SystemResource  according to values, if the value is null passes it,

     * @author umit.kas
     * @param pojo
     * @return SystemResource
     */
    @Override
    public SystemResource pojoToEntity(SystemResourcePojo pojo){
        SystemResource entity = new SystemResource();

        entity.setName(pojo.getName());
        entity.setPath(pojo.getPath());
        entity.setType(pojo.getType());
        entity.setOriginalFileName(pojo.getOriginalFileName());
        entity.setUrl(pojo.getUrl());
        entity.setPublicKey(pojo.getPublicKey());
        return  entity;
    }


    /**
     * Save entity list to database.

     * @author umit.kas
     * @param resources
     * @return boolean
     */
    @Override
    public boolean saveEntities(List<SystemResource> resources) {
        resources.stream().map(resource -> {
            resource.generatePublicKey();
            return resource;
        });
        systemResourceRepository.save(resources);
        return true;
    }

    /**
     * Saves the resource to db,
     * converst pojo to entity, generates publicKey, set visible,
     *
     * @param pojo
     * @return boolean
     * @author umit.kas
     */
    @Override
    public boolean save(SystemResourcePojo pojo) throws ServiceException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        SystemResource entity = this.pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setCreatedBy(authenticatedUser);
        entity = systemResourceRepository.save(entity);
        if (entity == null || entity.getId() == 0){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "No such a System resource is saved");
        }

        return true;
    }

    /**
     * map the paramater system announcement with system resource
     * finds the resource by publicKey then adds the announcement as paramater
     * then call repository save method to update

     * @author umit.kas
     * @param publicKey
     * @param announcement
     * @return boolean
     */
    @Override
    public boolean setResourceAnnouncement(String publicKey, SystemAnnouncement announcement) throws ServiceException{
        SystemResource entity = systemResourceRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "System announcement cannot be empty, for to set system resource announcement");
        }

        entity.setSystemAnnouncement(announcement);
        entity = systemResourceRepository.save(entity);

        if (entity != null && entity.getId() == 0){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "No such a system announcement of System resource is saved");
        }

        return true;
    }

    @Override
    public boolean save(List<SystemResourcePojo> pojos) {
        return false;
    }

    /**
     * finds the resource by name, if it is not null then converts it to pojo, and returns it.
     *
     * @author umit.kas
     * @param name
     * @return SystemResourcePojo
     */
    @Override
    public SystemResourcePojo getByName(String name)  throws ServiceException {
        SystemResource entity = systemResourceRepository.findByName(name);
        if (entity == null){
            throw new ServiceException(ExceptionType.NO_SUCH_DATA_NOT_FOUND, "System resource is not found by name");
        }

        return this.entityToPojo(entity);
    }


    /**
     * finds the resource by publicKey, if it is not null then converts it to pojo, and returns it.
     *
     * @author umit.kas
     * @param publicKey
     * @return SystemResourcePojo
     */
    @Override
    public SystemResourcePojo getByPublicKey(String publicKey)  throws ServiceException {
        SystemResource entity = systemResourceRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new ServiceException(ExceptionType.NO_SUCH_DATA_NOT_FOUND, "System resource is not found by public key");
        }

        return this.entityToPojo(entity);

    }


    /**
     * finds the resource by publicKey
     * if it is not null then
     * set visibility to false
     * and call save method of repository.
     *
     * @author umit.kas
     * @param publicKey
     * @return boolean
     */
    @Override
    public boolean delete(String publicKey) throws ServiceException {

        SystemResource entity = systemResourceRepository.findByPublicKey(publicKey);

        if (entity == null){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, String.format("No such a system resource is found publicKey: %s", publicKey));
        }
        entity.setVisible(false);
        entity = systemResourceRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, String.format("System resource is not deleted by publicKey: %s", publicKey));
        }
        return true;
    }
}
