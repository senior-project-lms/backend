package com.lms.services.impl;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import com.lms.entities.User;
import com.lms.enums.ExceptionType;
import com.lms.pojos.MailPojo;
import com.lms.pojos.SystemAnnouncementPojo;
import com.lms.pojos.SystemResourcePojo;
import com.lms.repositories.SystemAnnouncementRepository;
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
     * @param entity
     * @return SystemAnnouncementPojo
     */
    @Override
    public SystemAnnouncementPojo entityToPojo(SystemAnnouncement entity) {
        SystemAnnouncementPojo pojo = new SystemAnnouncementPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setUpdatedAt(entity.getUpdatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));

        List<SystemResourcePojo> resourcePojos = new ArrayList<>();
        for (SystemResource resourceEntity : entity.getResources()) {
            resourcePojos.add(systemResourceService.entityToPojo(resourceEntity));
        }
        pojo.setResources(resourcePojos);

        return pojo;
    }


    /**
     * Converts SystemAnnouncementPojo to SystemAnnouncement  according to values, if the value is null passes it,

     * @author umit.kas
     * @param pojo
     * @return SystemAnnouncement
     */
    @Override
    public SystemAnnouncement pojoToEntity(SystemAnnouncementPojo pojo){
        SystemAnnouncement entity = new SystemAnnouncement();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());

        if (pojo.getResources() != null){
            List<SystemResource> resourceEntities = new ArrayList<>();
            for(SystemResourcePojo resourcePojo : pojo.getResources()){
                resourceEntities.add(systemResourceService.pojoToEntity(resourcePojo));
            }
            entity.setResources(resourceEntities);
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
    public List<SystemAnnouncementPojo> getAllByPage(int page) throws ServiceException {
        List<SystemAnnouncementPojo> pojos = new ArrayList<>();

        List<SystemAnnouncement> entities = systemAnnouncementRepository.findAllByVisibleOrderByUpdatedAtDesc(true, new PageRequest(page, 5));

        if (entities == null){
            throw new ServiceException(ExceptionType.NO_SUCH_DATA_NOT_FOUND, "No such a system announcement is found");
        }

        for (SystemAnnouncement announcement : entities){
            pojos.add(this.entityToPojo(announcement));
        }
        return pojos;
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
    public boolean save(SystemAnnouncementPojo pojo) throws ServiceException{

        List<String> resourceKeys = pojo.getResourceKeys();

        User createdBy = customUserDetailService.getAuthenticatedUser();
        SystemAnnouncement entity = this.pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(createdBy);

        entity.setResources(null);
        entity = systemAnnouncementRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "System announcement is not saved");
        }




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
    public boolean delete(String publicKey) throws ServiceException{

        User deletedBy = customUserDetailService.getAuthenticatedUser();

        if (deletedBy == null){
            throw new SecurityException("Authenticated User is not found");
        }

        SystemAnnouncement entity = systemAnnouncementRepository.findByPublicKey(publicKey);
        if (entity == null){
            throw new ServiceException(ExceptionType.NO_SUCH_DATA_NOT_FOUND, String.format("No system announcement is found by publicKey: %s", publicKey));
        }
        entity.setUpdatedBy(deletedBy);
        entity.setVisible(false);
        entity = systemAnnouncementRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ServiceException(ExceptionType.EXECUTION_FAILS, "Delete system authentication process is not executed successfully");
        }
        return true;
    }


}

