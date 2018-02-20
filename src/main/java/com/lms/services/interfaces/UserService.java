package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.pojos.UserPojo;

import java.util.List;
import java.util.Map;

public interface UserService {


    UserPojo entityToPojo(User user);

    User pojoToEntity(UserPojo pojo);

    UserPojo getMe() throws DataNotFoundException;


    List<UserPojo> getAllByVisible(boolean visible) throws DataNotFoundException;

    UserPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(List<UserPojo> pojos) throws ExecutionFailException, DataNotFoundException;

    boolean updateVisibility(String publicKey, boolean visible)throws ExecutionFailException, DataNotFoundException;

    boolean userAlreadyExist(String user, String email);


    Map<String, Integer> getUserStatus();

    User findByEmail(String email) throws DataNotFoundException;

    User findByPublicKey(String publicKey) throws DataNotFoundException;

    void initialize() throws DataNotFoundException;

}
