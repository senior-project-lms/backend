package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Announcement;
import com.lms.pojos.course.AnnouncementPojo;

import java.util.List;

public interface CourseAnnouncementService {
    AnnouncementPojo entityToPojo(Announcement entity);

    Announcement pojoToEntity(AnnouncementPojo pojo);

    List<AnnouncementPojo> getAllByPage(String publicKey,int page) throws DataNotFoundException;

    boolean save(String coursePublicKey,AnnouncementPojo pojo) throws ExecutionFailException, DataNotFoundException;

    boolean delete(String coursePublicKey) throws DataNotFoundException, ExecutionFailException;

}
