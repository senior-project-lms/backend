package com.lms.services.interfaces;

import com.lms.entities.user.User;
import com.lms.pojos.user.UserPojo;

public interface UserService {


    UserPojo entityToPojo(User user, boolean authority, boolean ownedCourses, boolean registeredCoursesAsStudent) throws Exception;

    User pojoToEntity(UserPojo pojo) throws Exception;

    UserPojo getMe() throws Exception;


    }
