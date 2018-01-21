package com.lms.repositories.authority;

import com.lms.entities.authority.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by umit.kas on 28.11.2017.
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{


    Privilege findByCode(long code);

}
