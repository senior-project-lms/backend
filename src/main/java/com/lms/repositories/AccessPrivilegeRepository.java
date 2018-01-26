package com.lms.repositories;

import com.lms.entities.AccessPrivilege;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface AccessPrivilegeRepository extends JpaRepository<AccessPrivilege, Long>{

    AccessPrivilege findByUserAndPrivilegesIn(User user, Privilege privilege);

    AccessPrivilege findByUser(User user);
}
