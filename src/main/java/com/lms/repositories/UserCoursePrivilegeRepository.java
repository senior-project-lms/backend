package com.lms.repositories;

import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.entities.course.UserCoursePrivilege;
import com.lms.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface UserCoursePrivilegeRepository extends JpaRepository<UserCoursePrivilege, Long> {


    UserCoursePrivilege findByCourseAndPublicKey(Course course, String publicKey);

    UserCoursePrivilege findByCourseAndUser(Course course, User user);

    List<UserCoursePrivilege> findAllByCourseAndUserInAndVisible(Course course, User user, boolean visible);


    List<UserCoursePrivilege> findAllByUserAndVisible(User user, boolean visible);

    List<UserCoursePrivilege> findAllByUserInAndVisible(List<User> user, boolean visible);


    boolean existsByUser(User user);

    boolean existsByUserAndCourseAndPrivilegesContaining(User user, Course course, Privilege privilege);


}
