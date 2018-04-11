package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.course.Announcement;
import com.lms.pojos.course.CourseAnnouncementPojo;

import java.util.List;

public interface CourseAnnouncementService {
    CourseAnnouncementPojo entityToPojo(Announcement entity);

    Announcement pojoToEntity(CourseAnnouncementPojo pojo);

    List<CourseAnnouncementPojo> getAllByPage(String publicKey, int page) throws DataNotFoundException;

    boolean save(String coursePublicKey, CourseAnnouncementPojo pojo) throws ExecutionFailException, DataNotFoundException;

    boolean delete(String coursePublicKey) throws DataNotFoundException, ExecutionFailException;

}
