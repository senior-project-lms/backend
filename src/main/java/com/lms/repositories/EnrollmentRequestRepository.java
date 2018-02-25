package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.EnrollmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRequestRepository extends JpaRepository<EnrollmentRequest, Long> {

    List<EnrollmentRequest> findAllByCourseAndVisibleAndCancelled(Course course, boolean visible, boolean cancelled);

    EnrollmentRequest findByPublicKey(String publicKey);

    List<EnrollmentRequest> findAllByUserAndVisibleAndCancelled(User user, boolean visible, boolean cancelled);

    List<EnrollmentRequest> findAllByCourse(Course course);

    boolean existsByPublicKeyAndUser(String publicKey, User user);

    boolean existsByUserAndCourseAndRejectedAndCancelledAndEnrolled(User user, Course course, boolean rejected, boolean cancelled, boolean enrolled);

}
