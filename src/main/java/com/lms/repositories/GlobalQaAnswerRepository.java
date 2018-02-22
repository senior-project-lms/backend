package com.lms.repositories;

import com.lms.entities.GlobalQaAnswer;
import com.lms.entities.GlobalQaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface GlobalQaAnswerRepository extends JpaRepository<GlobalQaAnswer,Long> {

    List<GlobalQaAnswer> findAllByQuestion(GlobalQaQuestion question);

    GlobalQaAnswer findByPublicKey(String publicKey);

    List<GlobalQaAnswer> findAllByQuestionAndUpdatedAtGreaterThan(GlobalQaQuestion question, Date date);

}
