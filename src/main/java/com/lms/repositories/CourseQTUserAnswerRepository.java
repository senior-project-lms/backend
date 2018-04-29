package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.CourseQTAnswer;
import com.lms.entities.course.CourseQTQuestion;
import com.lms.entities.course.CourseQTUserAnswer;
import com.lms.entities.course.CourseQuizTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQTUserAnswerRepository extends JpaRepository<CourseQTUserAnswer, Long> {

    CourseQTUserAnswer findByQuestionAndCreatedByAndVisible(CourseQTQuestion question, User user, boolean visible);

    List<CourseQTUserAnswer> findByQtAndCreatedByAndVisible(CourseQuizTest qt, User user, boolean visible);


}
