package com.lms.repositories;

import com.lms.entities.course.QaQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface QaQuestionRepository extends JpaRepository<QaQuestion,Long> {

    List<QaQuestion> findAllByCourseAndVisible(boolean visible, Pageable pageable);

    QaQuestion findByCoursePublicKey(String publicKey);
}
