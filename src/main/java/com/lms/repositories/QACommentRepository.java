package com.lms.repositories;

import com.lms.entities.course.QA;
import com.lms.entities.course.QAComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QACommentRepository extends JpaRepository<QAComment, Long> {


    QAComment findByPublicKey(String publicKey);



}
