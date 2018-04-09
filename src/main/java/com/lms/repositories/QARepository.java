package com.lms.repositories;

import com.lms.entities.course.Course;
import com.lms.entities.course.QA;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface QARepository extends JpaRepository<QA, Long> {

    List<QA> findAllByCourseAndAnswerAndVisible(Course course, boolean answer, boolean visible, Pageable pageable);

    QA findByPublicKeyAndVisible(String publicKey, boolean visible);


    List<QA> findAllByParentAndVisible(QA parent, boolean visible);

    long countByParentAndVisible(QA parent, boolean visible);
}
