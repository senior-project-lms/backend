package com.lms.repositories;

import com.lms.entities.GlobalQAComment;
import com.lms.entities.GlobalQA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface GlobalQACommentRepository extends JpaRepository<GlobalQAComment,Long> {

    GlobalQAComment findByPublicKey(String publicKey);

}
