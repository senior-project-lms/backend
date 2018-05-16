package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQATag;
import com.lms.entities.User;
import com.lms.pojos.GlobalQATagPojo;
import com.lms.repositories.GlobalQATagRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.GlobalQATagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GlobalQATagServiceImpl implements GlobalQATagService {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private GlobalQATagRepository globalQATagRepository;

    @Override
    public GlobalQATagPojo entityToPojo(GlobalQATag entity) {
        GlobalQATagPojo pojo = new GlobalQATagPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setName(entity.getName());
        return pojo;
    }

    @Override
    public GlobalQATag pojoToEntity(GlobalQATagPojo pojo) {
        GlobalQATag entity = new GlobalQATag();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setName(pojo.getName());

        return entity;
    }

    @Override
    public List<GlobalQATag> save(List<GlobalQATagPojo> pojos) throws ExecutionFailException, DataNotFoundException {

        if (pojos == null) {
            throw new DataNotFoundException("pojo list cannot be empty");

        }
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        List<GlobalQATag> entities = pojos
                .stream()
                .map(pojo -> {
                    GlobalQATag entity = pojoToEntity(pojo);
                    entity.setCreatedBy(authenticatedUser);
                    entity.generatePublicKey();
                    return entity;
                })
                .collect(Collectors.toList());

        entities = globalQATagRepository.save(entities);

        if (entities == null) {
            throw new ExecutionFailException("Tags are not saved");
        }

        return entities;
    }

    @Override
    public List<GlobalQATag> findAllByPublicKeys(List<String> publicKeys) {
        List<GlobalQATag> entities = globalQATagRepository.findAllByPublicKeyInAndVisible(publicKeys, true);
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities;
    }


    @Override
    public List<GlobalQATagPojo> searchByName(String name) {

        List<GlobalQATag> entities = globalQATagRepository.findAllByNameContainsAndVisible(name, true);
        if (entities == null) {
            return new ArrayList<>();
        }

        List<GlobalQATagPojo> pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());


        return pojos;
    }
}
