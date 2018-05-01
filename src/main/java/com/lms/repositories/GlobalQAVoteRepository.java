package com.lms.repositories;

import com.lms.entities.GlobalQA;
import com.lms.entities.GlobalQAVote;
import com.lms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalQAVoteRepository extends JpaRepository<GlobalQAVote, Long>{

    long countByQaAndUpAndVisible(GlobalQA qa, boolean up, boolean visible);
    long countByQaAndDownAndVisible(GlobalQA qa, boolean down, boolean visible);

    long countByQaAndStarAndVisible(GlobalQA qa, boolean star, boolean visible);

    boolean existsByQaAndUpAndVisibleAndCreatedBy(GlobalQA qa, boolean up, boolean visible, User user);
    boolean existsByQaAndDownAndVisibleAndCreatedBy(GlobalQA qa, boolean down, boolean visible, User user);
    boolean existsByQaAndStarAndVisibleAndCreatedBy(GlobalQA qa, boolean star, boolean visible, User user);


    GlobalQAVote findByQaAndCreatedByAndStar(GlobalQA qa, User user, boolean star);

    GlobalQAVote findByQaAndCreatedBy(GlobalQA qa, User user);
}

