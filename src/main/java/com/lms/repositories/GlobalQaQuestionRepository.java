package com.lms.repositories;

import com.lms.entities.GlobalQaQuestion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
@Repository
public interface GlobalQaQuestionRepository extends JpaRepository<GlobalQaQuestion,Long>{

    List<GlobalQaQuestion> findAllByVisible(boolean visible, PageRequest pageable);

     GlobalQaQuestion findByPublicKey(String publicKey);
}
