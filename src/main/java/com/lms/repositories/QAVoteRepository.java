package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.CourseQAVote;
import com.lms.entities.course.CourseQA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QAVoteRepository extends JpaRepository<CourseQAVote, Long> {

    long countByQaAndUpAndVisible(CourseQA QA, boolean up, boolean visible);

    long countByQaAndDownAndVisible(CourseQA qa, boolean down, boolean visible);

    long countByQaAndStarAndVisible(CourseQA qa, boolean star, boolean visible);

    boolean existsByQaAndUpAndVisibleAndCreatedBy(CourseQA qa, boolean up, boolean visible, User user);

    boolean existsByQaAndDownAndVisibleAndCreatedBy(CourseQA qa, boolean down, boolean visible, User user);

    boolean existsByQaAndStarAndVisibleAndCreatedBy(CourseQA qa, boolean star, boolean visible, User user);


    CourseQAVote findByQaAndCreatedByAndStar(CourseQA qa, User user, boolean star);

    CourseQAVote findByQaAndCreatedBy(CourseQA qa, User user);
}
