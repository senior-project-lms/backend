package com.lms.repositories;

import com.lms.entities.course.QA;
import com.lms.entities.course.QaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QaAnswerRepository extends JpaRepository<QaComment, Long>{


    QaComment findByPublicKey(String publicKey);

    List<QaComment> findAllByQuestion(QA QA);


}
