package com.lms.repositories;

import java.util.List;

import com.lms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
