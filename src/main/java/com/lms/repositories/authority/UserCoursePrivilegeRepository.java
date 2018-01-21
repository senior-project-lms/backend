package com.lms.repositories.authority;

import com.lms.entities.authority.UserCoursePrivilege;
import com.lms.entities.course.Course;
import com.lms.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface UserCoursePrivilegeRepository extends JpaRepository<UserCoursePrivilege, Long>{


    UserCoursePrivilege findByCourseAndUser(Course course, User user);
}
