package com.lms.repositories.user;

import com.lms.entities.authority.Authority;
import com.lms.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	List<User> findAllByVisible(boolean visible);

	List<User> findAllByEnabledAndAuthority(boolean enabled, Authority authority);

	User findByPublicKey(String publicKey);

}
