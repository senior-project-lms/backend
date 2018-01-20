package com.lms.repositories;


import com.lms.entities.SystemAnnouncement;
import com.lms.entities.SystemResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SystemResourceRepository extends JpaRepository<SystemResource, Long>{

    List<SystemResource> findAllBySystemAnnouncement(SystemAnnouncement systemAnnouncement);
}
