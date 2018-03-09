package com.lms.repositories;

import com.lms.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority, Long> {


    Authority findByPublicKey(String publicKey);

    List<Authority> findAllByVisible(boolean visible);

    List<Authority> findAllByPublicKeyIn(List<String> publicKeys);

    Authority findByCode(long code);

}
