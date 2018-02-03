package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.User;
import com.lms.pojos.UserPojo;

import java.util.List;

public interface UserService {


    UserPojo entityToPojo(User user);

    User pojoToEntity(UserPojo pojo);

    UserPojo getMe() throws ServiceException;

    User findByEmail(String email) throws ServiceException;

    List<UserPojo> getAllByVisible(boolean visible) throws ServiceException;

    UserPojo getByPublicKey(String publicKey) throws ServiceException;

    boolean save(UserPojo pojo) throws ServiceException;

    boolean save(List<UserPojo> pojos) throws ServiceException;


}
