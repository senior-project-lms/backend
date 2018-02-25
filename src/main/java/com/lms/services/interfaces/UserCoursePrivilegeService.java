package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.User;
import com.lms.entities.UserCoursePrivilege;
import com.lms.entities.course.Course;

import java.util.List;

public interface UserCoursePrivilegeService {


    List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible) throws DataNotFoundException;

    boolean existByUser(User user);
}
