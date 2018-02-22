package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.EnrolmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolmentRequestRepository extends JpaRepository<EnrolmentRequest, Long> {

    List<EnrolmentRequest> findAllByCourseAndVisible(Course course, boolean visible);

    EnrolmentRequest findByPublicKey(String publicKey);

    List<EnrolmentRequest> findAllByUserAndVisible(User user, boolean visible);
}
