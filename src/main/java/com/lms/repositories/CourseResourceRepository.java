package com.lms.repositories;

import com.lms.entities.course.Course;
import com.lms.entities.course.CourseResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CourseResourceRepository extends JpaRepository<CourseResource, Long>{

    List<CourseResource> findAllByCourseAndVisible(Course course, boolean visible);

    CourseResource findByName(String name);

    CourseResource findByPublicKey(String publicKey);
}
