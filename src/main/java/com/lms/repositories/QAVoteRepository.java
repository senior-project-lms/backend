package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.QAVote;
import com.lms.entities.course.QA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QaVoteRepository  extends JpaRepository<QAVote, Long> {

    long countByQaAndUpAndVisible(QA QA, boolean up, boolean visible);
    long countByQaAndDownAndVisible(QA qa, boolean down, boolean visible);
    long countByQaAndStarAndVisible(QA qa, boolean star, boolean visible);

    boolean existsByQaAndUpAndVisibleAndCreatedBy(QA qa, boolean up, boolean visible, User user);
    boolean existsByQaAndDownAndVisibleAndCreatedBy(QA qa, boolean down, boolean visible, User user);
    boolean existsByQaAndStarAndVisibleAndCreatedBy(QA qa, boolean star, boolean visible, User user);


    QAVote findByQaAndCreatedByAndStar(QA qa, User user, boolean star);

    QAVote findByQaAndCreatedBy(QA qa, User user);
}
