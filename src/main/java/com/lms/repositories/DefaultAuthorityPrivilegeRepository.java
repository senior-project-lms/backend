package com.lms.repositories;

import com.lms.entities.DefaultAuthorityPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefaultAuthorityPrivilegeRepository extends JpaRepository<DefaultAuthorityPrivilege, Long> {


    List<DefaultAuthorityPrivilege> findAllByVisible(boolean visible);


}
