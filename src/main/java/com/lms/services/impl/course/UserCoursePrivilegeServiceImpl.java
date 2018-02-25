package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.User;
import com.lms.entities.UserCoursePrivilege;
import com.lms.repositories.UserCoursePrivilegeRepository;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserCoursePrivilegeServiceImpl implements UserCoursePrivilegeService {

    @Autowired
    private UserCoursePrivilegeRepository userCoursePrivilegeRepository;

    @Override
    public List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible) throws DataNotFoundException {

        List<UserCoursePrivilege> entities = userCoursePrivilegeRepository.findAllByUserAndVisible(user, visible);

        if (entities == null) {
            throw new DataNotFoundException("No such a not registered course is found for authenticated users");
        }

        return entities;
    }

    @Override
    public boolean existByUser(User user) {
        return userCoursePrivilegeRepository.existsByUser(user);
    }
}
