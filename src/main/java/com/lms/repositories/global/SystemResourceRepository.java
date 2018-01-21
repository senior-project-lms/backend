package com.lms.repositories.global;


import com.lms.entities.global.SystemAnnouncement;
import com.lms.entities.global.SystemResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SystemResourceRepository extends JpaRepository<SystemResource, Long>{

    List<SystemResource> findAllBySystemAnnouncement(SystemAnnouncement systemAnnouncement);
}
