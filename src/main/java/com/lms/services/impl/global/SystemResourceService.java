package com.lms.services.impl.global;

import com.lms.entities.global.SystemResource;
import com.lms.pojos.global.SystemResourcePojo;
import com.lms.repositories.global.SystemResourceRepository;
import com.lms.services.impl.global.SystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemResourceService {

    @Autowired
    SystemAnnouncementService systemAnnouncementService;

    @Autowired
    SystemResourceRepository systemResourceRepository;

    // file is not added inside the pojo and entity

    public SystemResourcePojo entityToPojo(SystemResource entity, boolean systemAnnouncement) throws Exception{

        SystemResourcePojo pojo = new SystemResourcePojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        pojo.setPath(entity.getPath());
        pojo.setType(entity.getType());

        if (systemAnnouncement){
            pojo.setAnnouncment(systemAnnouncementService.entityToPojo(entity.getSystemAnnouncement(), false));
        }
        return pojo;
    }

    public SystemResource pojoToEntity(SystemResourcePojo pojo) throws Exception{
        SystemResource entity = new SystemResource();

        entity.setName(pojo.getName());

        if (pojo.getPublicKey() != null){
            entity.setPublicKey(pojo.getPublicKey());
        }

        if (pojo.getAnnouncment() != null){
            entity.setSystemAnnouncement(systemAnnouncementService.pojoToEntity(pojo.getAnnouncment()));
        }

        return  entity;
    }

    public boolean save(List<SystemResource> resources) throws Exception{

        resources.stream().map(resource -> {
            resource.generatePublicKey();
            return resource;
        });
        systemResourceRepository.save(resources);
        return true;
    }
}
