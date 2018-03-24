package com.lms.repositories;

import com.lms.entities.SystemEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemEventRepository extends JpaRepository<SystemEvent, Long> {

    List<SystemEvent> findAllByVisible(boolean visible);

    SystemEvent findByPublicKey(String publicKey);
}
