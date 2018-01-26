package com.lms.services.impl;

import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.entities.User;
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
     * @param systemAnnouncement
     * @return SystemResourcePojo
     */
    @Override
    public SystemResourcePojo entityToPojo(SystemResource entity, boolean systemAnnouncement) throws Exception{

        SystemResourcePojo pojo = new SystemResourcePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        //pojo.setPath(entity.getPath());
        pojo.setUrl(entity.getUrl());
        pojo.setType(entity.getType());


        pojo.setOriginalFileName(entity.getOriginalFileName());
        if (systemAnnouncement){
            pojo.setAnnouncment(systemAnnouncementService.entityToPojo(entity.getSystemAnnouncement(), false));
        }
        return pojo;
    }


    /**
     * Converts SystemResourcePojo to SystemResource  according to values, if the value is null passes it,

     * @author umit.kas
     * @param pojo
     * @return SystemResource
     */
    @Override
    public SystemResource pojoToEntity(SystemResourcePojo pojo) throws Exception{
        SystemResource entity = new SystemResource();

        entity.setName(pojo.getName());
        entity.setPath(pojo.getPath());
        entity.setType(pojo.getType());
        entity.setOriginalFileName(pojo.getOriginalFileName());
        entity.setUrl(pojo.getUrl());
        if (pojo.getPublicKey() != null){
            entity.setPublicKey(pojo.getPublicKey());
        }

        if (pojo.getAnnouncment() != null){
            entity.setSystemAnnouncement(systemAnnouncementService.pojoToEntity(pojo.getAnnouncment()));
        }

        return  entity;
    }


    /**
     * Save entity list to database.

     * @author umit.kas
     * @param resources
     * @return boolean
     */
    @Override
    public boolean saveEntities(List<SystemResource> resources) throws Exception {
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
    public boolean save(SystemResourcePojo pojo) throws Exception {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        SystemResource entity = this.pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setCreatedBy(authenticatedUser);
        return systemResourceRepository.save(entity).getId() > 0;
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
    public boolean setResourceAnnouncement(String publicKey, SystemAnnouncement announcement) throws Exception{
        SystemResource entity = systemResourceRepository.findByPublicKey(publicKey);
        if (entity != null){
            entity.setSystemAnnouncement(announcement);
            return systemResourceRepository.save(entity).getId() > 0;
        }
        return false;

    }

    @Override
    public boolean save(List<SystemResourcePojo> pojos) throws Exception {
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
    public SystemResourcePojo getByName(String name) throws Exception {
        SystemResource entity = systemResourceRepository.findByName(name);
        if (entity != null){
            return this.entityToPojo(entity, false);
        }
        return null;
    }


    /**
     * finds the resource by publicKey, if it is not null then converts it to pojo, and returns it.
     *
     * @author umit.kas
     * @param publicKey
     * @return SystemResourcePojo
     */
    @Override
    public SystemResourcePojo getByPublicKey(String publicKey) throws Exception {
        SystemResource entity = systemResourceRepository.findByPublicKey(publicKey);
        if (entity != null){
            return this.entityToPojo(entity, false);
        }
        return null;
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
    public boolean delete(String publicKey) throws Exception{

        SystemResource entity = systemResourceRepository.findByPublicKey(publicKey);

        if (entity != null){
            return systemResourceRepository.save(entity).getId() > 0;
        }
        return false;
    }
}
