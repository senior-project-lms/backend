package com.lms.repositories;

import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQuizTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQTRepository extends JpaRepository<CourseQuizTest, Long> {


    List<CourseQuizTest> findAllByCourseAndVisibleAndPublished(Course course, boolean visible, boolean published);

    List<CourseQuizTest> findAllByCourseAndVisible(Course course, boolean visible);

    CourseQuizTest findByPublicKeyAndVisibleAndPublished(String publicKey, boolean visible, boolean published);

    CourseQuizTest findByPublicKeyAndVisible(String publicKey, boolean visible);


}
