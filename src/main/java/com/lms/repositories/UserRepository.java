package com.lms.repositories;

import com.lms.entities.Authority;
import com.lms.entities.PasswordResetToken;
import com.lms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByVisible(boolean visible);

    List<User> findAllByAuthorityAndVisible(Authority authority, boolean visible);

    User findByPublicKey(String publicKey);

    User findByEmail(String email);

    int countByVisible(boolean visible);

    boolean existsByUsernameOrEmail(String username, String email);

    boolean existByEmail(String Email);

    List<User> findAllByEmailLikeOrNameLikeOrSurnameLikeAndAuthorityAndVisible(String email, String name, String surname, Authority authority, boolean visible);

}

