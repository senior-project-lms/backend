package com.lms.repositories;

import com.lms.entities.GlobalQaAnswer;
import com.lms.entities.GlobalQaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface GlobalQaAnswerRepository extends JpaRepository<GlobalQaAnswer,Long> {

    List<GlobalQaAnswer> findAllByQuestion(GlobalQaQuestion question);

    GlobalQaAnswer findByPublicKey(String publicKey);

}
