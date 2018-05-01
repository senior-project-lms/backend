package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Grade;
import com.lms.entities.course.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findAllByGradeAndVisible(Grade grade, boolean visible);

    List<Score> findAllByGradeInAndStudentAndVisible(List<Grade> grades, User student, boolean visible);

    Score findByPublicKeyAndStudentAndVisible(String publicKey, User student, boolean visible);
}
