package com.lms.repositories.global;

import com.lms.entities.global.SystemAnnouncement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface SystemAnnouncementRepository extends JpaRepository<SystemAnnouncement, Long>{

    List<SystemAnnouncement> findAllByVisibleOrderByUpdatedAtDesc(boolean visible, Pageable pageable);

    SystemAnnouncement findByPublicKey(String publicKey);

}
