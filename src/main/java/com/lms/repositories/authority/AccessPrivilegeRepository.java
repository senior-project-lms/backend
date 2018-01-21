package com.lms.repositories.authority;

import com.lms.entities.authority.AccessPrivilege;
import com.lms.entities.authority.Privilege;
import com.lms.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface AccessPrivilegeRepository extends JpaRepository<AccessPrivilege, Long>{

    AccessPrivilege findByUserAndPrivilegesIn(User user, Privilege privilege);

    AccessPrivilege findByUser(User user);
}
