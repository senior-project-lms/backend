package com.lms.repositories;

import com.lms.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {


    Privilege findByCode(long code);

    List<Privilege> findAllByVisible(boolean visible);

    List<Privilege> findAllByPublicKeyIn(List<String> publicKeys);


    List<Privilege> findAllByCodeIn(List<Long> codes);

}
