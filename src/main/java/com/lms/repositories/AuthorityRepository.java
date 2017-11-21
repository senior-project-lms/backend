package com.lms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entities.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
