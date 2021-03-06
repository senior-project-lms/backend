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

    List<EnrollmentRequest> findAllByUserAndVisible(User user, boolean visible);

    List<EnrollmentRequest> findAllByCourse(Course course);

    List<EnrollmentRequest> findAllByPublicKeyIn(List<String> publicKeys);


    boolean existsByPublicKeyAndUser(String publicKey, User user);

    boolean existsByUserAndCourseAndRejectedAndCancelledAndEnrolledAndPending(User user, Course course, boolean rejected, boolean cancelled, boolean enrolled, boolean pending);

    boolean existsByUserAndCourseAndVisible(User user, Course course, boolean visible);


    EnrollmentRequest findByUserAndCourse(User user, Course course);

    int countByCourse_PublicKeyAndPendingAndVisible(String publicKey, boolean pending, boolean visible);

}
