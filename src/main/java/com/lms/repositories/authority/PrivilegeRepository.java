package com.lms.repositories.authority;

import com.lms.entities.authority.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{


    Privilege findByCode(long code);

}
