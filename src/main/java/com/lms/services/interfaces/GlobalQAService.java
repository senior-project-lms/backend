package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQA;
import com.lms.pojos.GlobalQAPojo;

import java.util.List;

public interface GlobalQAService {

    GlobalQAPojo entityToPojo(GlobalQA entity);

    GlobalQA pojoToEntity(GlobalQAPojo pojo);

    List<GlobalQAPojo> getAllByPage(int page) throws DataNotFoundException;

    GlobalQAPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    GlobalQA findByPublicKey(String publicKey, boolean visible) throws DataNotFoundException;

    boolean save(GlobalQAPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean delete(String publicKey) throws DataNotFoundException,ExecutionFailException;

    boolean update(String publicKey, GlobalQAPojo pojo)throws DataNotFoundException,ExecutionFailException;
}

//findBy entity