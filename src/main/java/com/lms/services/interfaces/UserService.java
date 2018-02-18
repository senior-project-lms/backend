package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.pojos.UserPojo;

import java.util.List;

public interface UserService {


    UserPojo entityToPojo(User user);

    User pojoToEntity(UserPojo pojo);

    UserPojo getMe() throws DataNotFoundException;

    User findByEmail(String email) throws DataNotFoundException;

    List<UserPojo> getAllByVisible(boolean visible) throws DataNotFoundException;

    UserPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(UserPojo pojo) throws DataNotFoundException, ExecutionFailException;

    boolean save(List<UserPojo> pojos) throws ExecutionFailException, DataNotFoundException;


    void initialize() throws DataNotFoundException;

}
