package com.lms.services.impl.global;

import com.lms.entities.global.SystemAnnouncement;
import com.lms.entities.global.SystemResource;
import com.lms.entities.user.User;
import com.lms.pojos.MailPojo;
import com.lms.pojos.global.SystemAnnouncementPojo;
import com.lms.pojos.global.SystemResourcePojo;
import com.lms.pojos.user.UserPojo;
import com.lms.repositories.global.SystemAnnouncementRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.MailService;
import com.lms.services.interfaces.SystemAnnouncementService;
import com.lms.services.interfaces.SystemResourceService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemAnnouncementServiceImpl implements SystemAnnouncementService{


    @Autowired
    private SystemAnnouncementRepository systemAnnouncementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemResourceService systemResourceService;


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private MailService mailService;



    /**
     * Converts SystemAnnouncement entity to SystemAnnouncement pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author umit.kas
     * @param entity, systemResource
     * @return SystemAnnouncementPojo
     */
    @Override
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


    /**
     * Converts SystemAnnouncementPojo to SystemAnnouncement  according to values, if the value is null passes it,

     * @author umit.kas
     * @param pojo
     * @return SystemAnnouncement
     */
    @Override
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


    /**
     *
     * Returns a list of 5 SystemAnnouncementPojos,
     * Selects the visible SystemAnnouncement by desc order, converts it to pojo than returns
     *
     * @author umit.kas
     * @param page
     * @return  List<SystemAnnouncementPojo>
     */
    @Override
    public List<SystemAnnouncementPojo> getAnnouncements(int page) throws Exception{
        List<SystemAnnouncementPojo> announcementPojos = new ArrayList<>();

        for (SystemAnnouncement announcement : systemAnnouncementRepository.findAllByVisibleOrderByUpdatedAtDesc(true, new PageRequest(page, 5))){
            announcementPojos.add(this.entityToPojo(announcement, true));
        }
        return announcementPojos;
    }



    /**
     *
     * Save the SystemAnnouncement, converts input pojo to entity, also convers resources to entity
     * add who creates(authenticated user)
     * generates publicKey
     * put resources to a list
     * then set null resource list in entity
     * set visible to entity
     * save entity
     * then iterate resources, add who create it, visibilty and announcement, which is saved
     * then save the resources by its service
     * then sent mail to all users via mail service
     *
     * @author umit.kas
     * @param pojo
     * @return boolean
     */
    @Override
    public boolean save(SystemAnnouncementPojo pojo) throws Exception{

        List<String> resourceKeys = pojo.getResourceKeys();

        User createdBy = customUserDetailService.getAuthenticatedUser();
        SystemAnnouncement entity = this.pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(createdBy);

        entity.setResources(null);
        entity.setVisible(true);
        entity = systemAnnouncementRepository.save(entity);


        if (entity.getId() > 0 && resourceKeys != null){

            for (String key: resourceKeys) {
                systemResourceService.setResourceAnnouncement(key, entity);
            }
            // mail stuff

            // get mail addresses of all visible users
            List<String> emailAddresses = userService.getAllByVisible(true).stream().map(user -> user.getEmail()).collect(Collectors.toList());

            MailPojo mailPojo = new MailPojo();
            mailPojo.setTo(emailAddresses);
            mailPojo.setSubject(String.format("%s", "System Announcement"));
            String mailText = String.format("%s", pojo.getContent());
            mailPojo.setText(mailText);

            // open later, tested, but mail informations will not shared on github
            //mailService.send(mailPojo);

            return true;
        }

        return false;

    }



    /**
     *
     * Delete the system announcement,
     * Delete means set visible false to property of it.
     * add who deletes it,
     * update visibily false
     * then save it.
     *
     * @author umit.kas
     * @param publicKey
     * @return boolean
     */
    @Override
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

