package com.lms.repositories.user;

import com.lms.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
