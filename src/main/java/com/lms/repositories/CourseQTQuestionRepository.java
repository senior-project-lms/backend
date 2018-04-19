package com.lms.repositories;

import com.lms.entities.course.CourseQTQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseQTQuestionRepository extends JpaRepository<CourseQTQuestion, Long> {


    CourseQTQuestion findByPublicKeyAndVisible(String publicKey, boolean visible);

}
