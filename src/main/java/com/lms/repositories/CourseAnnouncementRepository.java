package com.lms.repositories;

import com.lms.entities.course.Announcement;
import com.lms.entities.course.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CourseAnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByCourseAndVisibleOrderByUpdatedAtDesc(Course course,boolean visible, Pageable pageable);

    Announcement findByPublicKey(String publicKey);
}
