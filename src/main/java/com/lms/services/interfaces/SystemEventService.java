package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.SystemEvent;
import com.lms.pojos.SystemEventPojo;

import java.util.List;

public interface SystemEventService {


    SystemEvent pojoToEntity(SystemEventPojo pojo);

    SystemEventPojo entityToPojo(SystemEvent entity);


    boolean save(SystemEventPojo pojo) throws ExecutionFailException;

    boolean delete(String publicKey) throws DataNotFoundException, ExecutionFailException;

    List<SystemEventPojo> getAllEvents();
}
