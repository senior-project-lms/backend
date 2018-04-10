package com.lms.repositories;

import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQA;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface QARepository extends JpaRepository<CourseQA, Long> {

    List<CourseQA> findAllByCourseAndAnswerAndVisible(Course course, boolean answer, boolean visible, Pageable pageable);

    CourseQA findByPublicKeyAndVisible(String publicKey, boolean visible);


    List<CourseQA> findAllByParentAndVisible(CourseQA parent, boolean visible);

    long countByParentAndVisible(CourseQA parent, boolean visible);
}
