package com.lms.services.custom;

import com.lms.configurations.CustomUserDetails;
import com.lms.entities.User;
import com.lms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);

	}

	/**
	 *
	 * Returns authenticated user,
	 * Finds username by SecurityContextHolder, then finds the entity by username returns it
	 *
	 * @author umit.kas
	 * @param
	 * @return User
	 *
	 */
	public User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return this.findByUsername(username);
		
	}
	
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}


	/**
	 *
	 * Save user,
	 * Used in App class,
	 * Don't userin services
	 *
	 * @author umit.kas
	 * @param user
	 * @return User
	 *
	 */
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPublicKey(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

}
