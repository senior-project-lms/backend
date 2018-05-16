package com.lms.repositories;

import com.lms.entities.GlobalQATag;
import com.lms.entities.course.CourseQATag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalQATagRepository extends JpaRepository<GlobalQATag, Long> {

    List<GlobalQATag> findAllByPublicKeyInAndVisible(List<String> publicKeys, boolean visible);

    List<GlobalQATag> findAllByNameContainsAndVisible(String name, boolean visible);
}
