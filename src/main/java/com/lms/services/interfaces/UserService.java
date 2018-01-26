package com.lms.services.interfaces;

import com.lms.entities.User;
import com.lms.pojos.UserPojo;

import java.util.List;

public interface UserService {


    UserPojo entityToPojo(User user, boolean authority, boolean ownedCourses, boolean registeredCoursesAsStudent) throws Exception;

    User pojoToEntity(UserPojo pojo) throws Exception;

    UserPojo getMe() throws Exception;

    List<UserPojo> getAllByVisible(boolean visible) throws Exception;

    UserPojo getUser(String publicKey) throws  Exception;

    boolean save(UserPojo pojo) throws  Exception;

    boolean save(List<UserPojo> pojos) throws  Exception;


}
