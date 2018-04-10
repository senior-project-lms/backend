package com.lms.services.interfaces;

import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQATag;
import com.lms.pojos.GlobalQATagPojo;

import java.util.List;

public interface GlobalQATagService {


    GlobalQATagPojo entityToPojo(GlobalQATag entity);

    GlobalQATag pojoToEntity(GlobalQATagPojo pojo);

    List<GlobalQATag> save(List<GlobalQATagPojo> pojos) throws ExecutionFailException;

    List<GlobalQATag> findAllByPublicKeys(List<String> publicKeys);

    List<GlobalQATagPojo> searchByName(String name);


}
