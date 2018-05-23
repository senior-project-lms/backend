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

    List<User> findAllByPublicKeyIn(List<String> publicKeys);

    List<User> findAllByEmailLikeOrNameLikeOrSurnameLikeAndAuthorityAndVisible(String email, String name, String surname, Authority authority, boolean visible);

    List<User> findAllByVisibleAndNameContainingOrSurnameContaining(boolean visible, String name, String surname);

    List<User> findAllByVisibleAndNameContaining(boolean visible, String name);

    List<User> findAllByVisibleAndSurnameContaining(boolean visible, String surname);

    List<User> findAllByAuthorityInAndVisible(List<Authority> authorities, boolean visible);

    List<User> findAllByNameContainsAndAuthorityInAndVisible(String name, List<Authority> authorities, boolean visible);

    List<User> findAllBySurnameContainsAndAuthorityInAndVisible(String surname, List<Authority> authorities, boolean visible);

    List<User> findAllByUsernameNotInAndVisible(List<String> usernames, boolean visible);

    List<User> findAllByUsernameInAndVisible(List<String> usernames, boolean visible);
}

