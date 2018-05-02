package com.lms.repositories;

import com.lms.entities.course.Course;
import com.lms.entities.course.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseGradeRepository extends JpaRepository<Grade, Long> {

    Grade findByPublicKeyAndCourseAndVisible(String publicKey, Course course, boolean visible);

    List<Grade> findAllByCourseAndVisible(Course course, boolean visible);

    Grade findByPublicKeyAndVisible(String publicKey, boolean visible);
}
