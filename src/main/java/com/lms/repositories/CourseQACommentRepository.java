package com.lms.repositories;

import com.lms.entities.course.CourseQAComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseQACommentRepository extends JpaRepository<CourseQAComment, Long> {


    CourseQAComment findByPublicKey(String publicKey);



}
