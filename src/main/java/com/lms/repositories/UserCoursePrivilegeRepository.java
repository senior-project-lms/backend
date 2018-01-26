package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.UserCoursePrivilege;
import com.lms.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface UserCoursePrivilegeRepository extends JpaRepository<UserCoursePrivilege, Long> {


    UserCoursePrivilege findByCourseAndUser(Course course, User user);
}
