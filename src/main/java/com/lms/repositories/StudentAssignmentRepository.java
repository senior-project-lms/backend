package com.lms.repositories;

import com.lms.entities.User;
import com.lms.entities.course.Assignment;
import com.lms.entities.course.Course;
import com.lms.entities.course.StudentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAssignmentRepository extends JpaRepository<StudentAssignment,Long>{

    List<StudentAssignment> findAllByAssignment(Assignment assignment);

    StudentAssignment findByAssignmentAndCreatedBy(Assignment assignment, User user);

    StudentAssignment findByPublicKey(String publicKey);

    int countByAssignmentInAndCreatedByAndVisible(List<Assignment> assignments, User user, boolean visible);

}
