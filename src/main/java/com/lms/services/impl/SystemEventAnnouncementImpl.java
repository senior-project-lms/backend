package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.SystemEvent;
import com.lms.entities.User;
import com.lms.pojos.SystemEventPojo;
import com.lms.repositories.SystemEventRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.SystemEventService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemEventAnnouncementImpl implements SystemEventService {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private SystemEventRepository systemEventRepository;

    @Override
    public SystemEvent pojoToEntity(SystemEventPojo pojo) {

        SystemEvent entity = new SystemEvent();

        entity.setPublicKey(pojo.getPublicKey());
        entity.setTitle(pojo.getTitle());
        entity.setStart(pojo.getStart());
        entity.setEnd(pojo.getEnd());


        return entity;
    }

    @Override
    public SystemEventPojo entityToPojo(SystemEvent entity) {
        SystemEventPojo pojo = new SystemEventPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setTitle(entity.getTitle());
        pojo.setStart(entity.getStart());
        pojo.setEnd(entity.getEnd());

        return pojo;
    }

    @Override
    public boolean save(SystemEventPojo pojo) {
        User authUser = userDetailService.getAuthenticatedUser();

        SystemEvent entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCreatedBy(authUser);


        entity = systemEventRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ServiceException("No such a system event is saved");
        }

        return true;
    }

    @Override
    public boolean delete(String publicKey) throws DataNotFoundException, ServiceException {

        SystemEvent entity = systemEventRepository.findByPublicKey(publicKey);


        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a system event is found by publicKey: %s", publicKey));
        }

        entity.setVisible(false);
        entity = systemEventRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ServiceException(String.format("No such a system event is deleted by publicKey: %s", publicKey));
        }
        return true;
    }

    @Override
    public List<SystemEventPojo> getAllEvents() {

        List<SystemEvent> entities = systemEventRepository.findAllByVisible(true);

        if (entities == null) {
            new ArrayList<>();
        }

        List<SystemEventPojo> pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }
}
