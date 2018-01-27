package com.lms.repositories;

import com.lms.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAccessLevel(long accessLevel);

    Authority findByPublicKey(String publicKey);
}
