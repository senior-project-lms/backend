package com.lms.repositories;

import com.lms.entities.course.QaAnswer;
import com.lms.entities.course.QaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QaAnswerRepository extends JpaRepository<QaAnswer, Long>{


    QaAnswer findByPublicKey(String publicKey);

    List<QaAnswer> findAllByQuestion(QaQuestion qaQuestion);


}
