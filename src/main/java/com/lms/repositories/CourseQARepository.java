package com.lms.repositories;

import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQA;
import com.lms.entities.course.CourseQATag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface CourseQARepository extends JpaRepository<CourseQA, Long> {

    List<CourseQA> findAllByCourseAndAnswerAndVisibleOrderByCreatedAtDesc(Course course, boolean answer, boolean visible, Pageable pageable);

    CourseQA findByPublicKeyAndVisible(String publicKey, boolean visible);


    List<CourseQA> findAllByParentAndVisible(CourseQA parent, boolean visible);

    long countByParentAndVisible(CourseQA parent, boolean visible);

    List<CourseQA> findTop10ByTagsInAndCourseAndVisibleOrderByCreatedAtDesc(List<CourseQATag> tags, Course course, boolean visible);

}
