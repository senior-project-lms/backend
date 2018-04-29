package com.lms.repositories;

import com.lms.entities.course.CourseQTAnswer;
import com.lms.entities.course.CourseQTQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseQTAnswerRepository extends JpaRepository<CourseQTAnswer, String> {

    CourseQTAnswer findByPublicKeyAndQuestionAndVisible(String publicKey, CourseQTQuestion question, boolean visible);


    CourseQTAnswer findByPublicKeyAndVisible(String publicKey, boolean visible);

    List<CourseQTAnswer> findByPublicKeyInAndVisible(List<String> publicKey, boolean visible);
}
