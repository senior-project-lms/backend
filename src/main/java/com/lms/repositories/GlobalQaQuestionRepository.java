package com.lms.repositories;

import com.lms.entities.GlobalQaQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface GlobalQaQuestionRepository extends JpaRepository<GlobalQaQuestion,Long>{

    List<GlobalQaQuestion> findAllByVisible(boolean visible, Pageable pageable);

     GlobalQaQuestion findByPublicKey(String publicKey);
}
