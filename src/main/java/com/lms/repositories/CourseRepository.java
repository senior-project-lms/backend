package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByPublicKey(String publicKey);

    List<Course> findAllByRegisteredUsersIn(List<User> registeredUsers);

    List<Course> findAllByVisible(boolean visible);

    boolean existsByCode(String code);


}
