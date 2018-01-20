package com.lms.repositories;

import com.lms.entities.Course;
import com.lms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Repository
public interface CourseRepositoy extends JpaRepository<Course, Long> {

    Course findByPublicKey(String publicKey);

    List<Course> findAllByRegisteredUsersIn(List<User> registeredUsers);
}
