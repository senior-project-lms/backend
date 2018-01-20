package com.lms.services;

import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.entities.User;
import com.lms.pojos.SystemAnnouncementPojo;
import com.lms.pojos.SystemResourcePojo;
import com.lms.repositories.SystemAnnouncementRepository;
import com.lms.services.custom.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemAnnouncementService {


    @Autowired
    private SystemAnnouncementRepository systemAnnouncementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemResourceService systemResourceService;


    @Autowired
    private CustomUserDetailService customUserDetailService;

    public SystemAnnouncementPojo entityToPojo(SystemAnnouncement entity, boolean systemResource) throws Exception{
        SystemAnnouncementPojo pojo = new SystemAnnouncementPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setUpdatedAt(entity.getUpdatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy(), false, false, false));

        if (systemResource) {
            List<SystemResourcePojo> resourcePojos = new ArrayList<>();
            for (SystemResource resourceEntity : entity.getResources()) {
                resourcePojos.add(systemResourceService.entityToPojo(resourceEntity, false));
            }
            pojo.setResources(resourcePojos);
        }

        return pojo;
    }


    public SystemAnnouncement pojoToEntity(SystemAnnouncementPojo pojo) throws Exception{
        SystemAnnouncement entity = new SystemAnnouncement();

        if (pojo.getPublicKey() != null){
            entity.setPublicKey(pojo.getPublicKey());
        }

        if (pojo.getTitle() != null){
            entity.setTitle(pojo.getTitle());
        }

        if (pojo.getContent() != null){
            entity.setContent(pojo.getContent());
        }

        if (pojo.getResources() != null){
            List<SystemResource> resourceEntites = new ArrayList<>();
            for(SystemResourcePojo resourcePojo : pojo.getResources()){
                resourceEntites.add(systemResourceService.pojoToEntity(resourcePojo));
            }
            entity.setResources(resourceEntites);
        }

        return entity;
    }


    public List<SystemAnnouncementPojo> getAnnouncements(int page) throws Exception{
        List<SystemAnnouncementPojo> announcementPojos = new ArrayList<>();

        for (SystemAnnouncement announcement : systemAnnouncementRepository.findAllByVisibleOrderByUpdatedAtDesc(true, new PageRequest(page, 5))){
            announcementPojos.add(this.entityToPojo(announcement, true));
        }
        return announcementPojos;
    }

    public boolean save(SystemAnnouncementPojo pojo) throws Exception{

        User createdBy = customUserDetailService.getAuthenticatedUser();
        SystemAnnouncement entity = this.pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(createdBy);
        if (entity.getResources() != null){

            entity.getResources().stream().map(resource -> {
                resource.setCreatedBy(createdBy);
                return resource;
            });

        }

        entity.setVisible(true);
        return systemAnnouncementRepository.save(entity).getId() > 0;
    }


    public boolean delete(String publicKey) throws Exception{

        User deletedBy = customUserDetailService.getAuthenticatedUser();

        SystemAnnouncement entity = systemAnnouncementRepository.findByPublicKey(publicKey);
        if (entity != null){
            entity.setVisible(false);
            entity.setUpdatedBy(deletedBy);
            return systemAnnouncementRepository.save(entity).getId() > 0;

        }
        return false;
    }


}

