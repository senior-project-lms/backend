package com.lms.repositories.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entities.authority.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
