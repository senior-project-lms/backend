package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.SystemAnnouncement;
import com.lms.pojos.SystemAnnouncementPojo;

import java.util.List;

public interface SystemAnnouncementService {

    SystemAnnouncementPojo entityToPojo(SystemAnnouncement entity, boolean systemResource);

    SystemAnnouncement pojoToEntity(SystemAnnouncementPojo pojo);

    List<SystemAnnouncementPojo> getAnnouncements(int page) throws ServiceException;

    boolean save(SystemAnnouncementPojo pojo) throws ServiceException;

    boolean delete(String publicKey) throws ServiceException;

}

