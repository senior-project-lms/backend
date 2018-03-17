package com.lms.repositories;


import com.lms.entities.course.Announcement;
import com.lms.entities.course.Course;
import com.lms.entities.course.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CourseEventRepository extends JpaRepository<Event, Long> {

    Event findByPublicKeyAndVisible(String publicKey, boolean visible);

    List<Event> findAllByCourseAndVisible(Course course, boolean visible);

    List<Event> findAllByVisible(boolean visible);


}
