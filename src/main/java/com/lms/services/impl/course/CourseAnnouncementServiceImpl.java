package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Announcement;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import com.lms.pojos.course.AnnouncementPojo;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.repositories.CourseAnnouncementRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.CourseAnnouncementService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseAnnouncementServiceImpl implements CourseAnnouncementService {

    @Autowired
    private UserService userService;


    @Autowired
    private CourseAnnouncementRepository courseAnnouncementRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseService courseService;


    @Override
    public AnnouncementPojo entityToPojo(Announcement entity) {
        AnnouncementPojo pojo = new AnnouncementPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        pojo.setTitle(entity.getTitle());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setUpdatedAt(entity.getUpdatedAt());
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));


        return pojo;
    }

    @Override
    public Announcement pojoToEntity(AnnouncementPojo pojo) {
        Announcement entity = new Announcement();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setTitle(pojo.getTitle());
        entity.setContent(pojo.getContent());


        return entity;

    }

    @Override
    public List<AnnouncementPojo> getAllByPage(String publicKey, int page) throws DataNotFoundException {
        List<AnnouncementPojo> pojos = new ArrayList<>();

        Course course = courseService.findByPublicKey(publicKey);

        List<Announcement> entities = courseAnnouncementRepository.findAllByCourseAndVisibleOrderByUpdatedAtDesc(course, true, new PageRequest(page, 5));

        if (entities == null) {
            throw new DataNotFoundException("No such a course announcement is found");
        }

        for (Announcement announcement : entities) {
            pojos.add(this.entityToPojo(announcement));
        }
        return pojos;

    }

    @Override
    public boolean save(String coursePublicKey, AnnouncementPojo pojo) throws ExecutionFailException, DataNotFoundException {
        User createdBy = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        Announcement entity = this.pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setCourse(course);

        entity.setCreatedBy(createdBy);
        entity = courseAnnouncementRepository.save(entity);
        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Course announcement is not saved");
        }
        return true;
    }

    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        User deletedBy = customUserDetailService.getAuthenticatedUser();

        if (deletedBy == null) {
            throw new SecurityException("Authenticated User is not found");
        }
        Announcement entity = courseAnnouncementRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException(String.format("No course announcement is found by publicKey: %s", publicKey));
        }
        entity.setUpdatedBy(deletedBy);
        entity.setVisible(false);
        entity = courseAnnouncementRepository.save(entity);
        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("Deleting course announcement process is not successfully executed.");
        }

        return true;
    }

}
