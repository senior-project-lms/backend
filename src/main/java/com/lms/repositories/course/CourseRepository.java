package com.lms.repositories.course;

import com.lms.entities.course.Course;
import com.lms.entities.user.User;
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
}
