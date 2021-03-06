package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findByPublicKey(String publicKey);

    List<Course> findAllByVisible(boolean visible);

    boolean existsByCode(String code);

    int countByVisible(boolean visible);

    List<Course> findAllByRegisteredUsersNotContainsAndVisible(User user, boolean visible);

    List<Course> findAllByRegisteredUsersNotContainsAndVisibleAndCodeContaining(User user, boolean visible, String param);

    List<Course> findAllByRegisteredUsersNotContainsAndVisibleAndNameContaining(User user, boolean visible, String param);

    List<Course> findAllByRegisteredUsersNotContainsAndVisibleAndOwnerIn(User user, boolean visible, List<User> lecturer);

    List<Course> findAllByRegisteredUsersContainsAndAssistantUsersNotContainsAndVisible(User user, User user2, boolean visible);

    List<Course> findAllByObserverUsersContainsAndAssistantUsersNotContainsAndVisible(User user, User user2, boolean visible);

    List<Course> findAllByAssistantUsersContainsAndVisible(User user, boolean visible);

    List<Course> findAllByOwnerAndVisible(User user, boolean visible);



}
