package com.lms.services.interfaces;

import com.lms.entities.global.SystemAnnouncement;
import com.lms.pojos.global.SystemAnnouncementPojo;

import java.util.List;

public interface SystemAnnouncementService {

    SystemAnnouncementPojo entityToPojo(SystemAnnouncement entity, boolean systemResource) throws Exception;

    SystemAnnouncement pojoToEntity(SystemAnnouncementPojo pojo) throws Exception;

    List<SystemAnnouncementPojo> getAnnouncements(int page) throws Exception;

    boolean save(SystemAnnouncementPojo pojo) throws Exception;

    boolean delete(String publicKey) throws Exception;

}

