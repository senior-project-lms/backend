package com.lms.services.interfaces;

import com.lms.customExceptions.ServiceException;
import com.lms.entities.User;
import com.lms.pojos.UserPojo;

import java.util.List;

public interface UserService {


    UserPojo entityToPojo(User user, boolean authority, boolean ownedCourses, boolean registeredCoursesAsStudent);

    User pojoToEntity(UserPojo pojo);

    UserPojo getMe() throws ServiceException;

    List<UserPojo> getAllByVisible(boolean visible) throws ServiceException;

    UserPojo getUser(String publicKey) throws ServiceException;

    boolean save(UserPojo pojo) throws ServiceException;

    boolean save(List<UserPojo> pojos) throws ServiceException;


}
