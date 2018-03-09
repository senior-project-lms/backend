package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.SystemAnnouncement;
import com.lms.pojos.SystemAnnouncementPojo;

import java.util.List;

public interface SystemAnnouncementService {

    SystemAnnouncementPojo entityToPojo(SystemAnnouncement entity);

    SystemAnnouncement pojoToEntity(SystemAnnouncementPojo pojo);

    List<SystemAnnouncementPojo> getAllByPage(int page) throws DataNotFoundException;

    boolean save(SystemAnnouncementPojo pojo) throws ExecutionFailException, DataNotFoundException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

}

