package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQTUser;
import com.lms.entities.course.CourseQuizTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQTUserRepository extends JpaRepository<CourseQTUser, Long> {


    CourseQTUser findByQtAndCreatedByAndVisible(CourseQuizTest qt, User user, boolean visible);

    boolean existsByQtAndCreatedByAndVisible(CourseQuizTest qt, User user, boolean visible);

    boolean existsByQtAndCreatedByAndVisibleAndFinished(CourseQuizTest qt, User user, boolean visible, boolean finished);

    CourseQTUser findByPublicKeyAndVisible(String publicKey, boolean visible);

    boolean existsByPublicKeyAndVisible(String publicKey, boolean visible);


    List<CourseQTUser> findByQtAndVisible(CourseQuizTest qt, boolean visible);

    int countByCourseAndCreatedByAndVisible(Course course, User user, boolean visible);

}
