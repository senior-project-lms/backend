package com.lms.repositories;

import com.lms.entities.course.Assignment;
import com.lms.entities.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseAssignmentRepository extends JpaRepository<Assignment,Long>{

    List<Assignment> findAllByCourseAndVisible(Course course, boolean visible);

    List<Assignment> findAllByCourseAndVisibleAndPublished(Course course, boolean visible, boolean published);

    Assignment findByPublicKey(String publicKey);

    int countByCourseAndPublishedAndVisible(Course course, boolean published, boolean visible);
}
