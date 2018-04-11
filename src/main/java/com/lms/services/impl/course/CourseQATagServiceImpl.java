package com.lms.services.impl.course;

import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.CourseQATag;
import com.lms.pojos.course.CourseQATagPojo;
import com.lms.repositories.CourseQATagRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQATagService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQATagServiceImpl implements CourseQATagService {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseQATagRepository CourseQATagRepository;

    @Override
    public CourseQATagPojo entityToPojo(CourseQATag entity) {
        CourseQATagPojo pojo = new CourseQATagPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        return pojo;
    }

    @Override
    public CourseQATag pojoToEntity(CourseQATagPojo pojo) {
        CourseQATag entity = new CourseQATag();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setName(pojo.getName());

        return entity;
    }

    @Override
    public List<CourseQATag> save(List<CourseQATagPojo> pojos) throws ExecutionFailException {

        if (pojos == null) {
            throw new ServiceException("pojo list cannot be empty");

        }
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        List<CourseQATag> entities = pojos
                .stream()
                .map(pojo -> {
                    CourseQATag entity = pojoToEntity(pojo);
                    entity.setCreatedBy(authenticatedUser);
                    entity.generatePublicKey();
                    return entity;
                })
                .collect(Collectors.toList());

        entities = CourseQATagRepository.save(entities);

        if (entities == null) {
            throw new ExecutionFailException("Tags are not saved");
        }

        return entities;
    }

    @Override
    public List<CourseQATag> findAllByPublicKeys(List<String> publicKeys) {
        List<CourseQATag> entities = CourseQATagRepository.findAllByPublicKeyInAndVisible(publicKeys, true);
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities;
    }


    @Override
    public List<CourseQATagPojo> searchByName(String name) {

        List<CourseQATag> entities = CourseQATagRepository.findAllByNameContainsAndVisible(name, true);
        if (entities == null) {
            return new ArrayList<>();
        }

        List<CourseQATagPojo> pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());


        return pojos;
    }
}
