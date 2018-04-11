package com.lms.repositories;

import com.lms.entities.course.CourseQATag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQATagRepository extends JpaRepository<CourseQATag, Long> {

    List<CourseQATag> findAllByPublicKeyInAndVisible(List<String> publicKeys, boolean visible);

    List<CourseQATag> findAllByNameContainsAndVisible(String name, boolean visible);
}
