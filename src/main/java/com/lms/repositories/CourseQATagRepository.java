package com.lms.repositories;

import com.lms.entities.course.CourseQATag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseQATagRepository extends JpaRepository<CourseQATag, Long> {
}
