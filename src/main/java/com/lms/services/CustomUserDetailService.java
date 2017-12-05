package com.lms.services;

import java.util.List;
import java.util.UUID;

import com.lms.entities.User;
import com.lms.repositories.UserRepository;
import com.lms.configurations.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPublicId(UUID.randomUUID().toString());
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);

	}
	
	public User getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		return this.findByUsername(username);
		
	}
	
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	public List<User> findByUsernameContaining(String username){
		return this.userRepository.findByUsernameContaining(username);
	}
	
	
	public List<User> findByPublicIdIn(List<String> publicIds){
		return this.userRepository.findByPublicIdIn(publicIds);
	}
	
	
	public User findByPublicId(String publicId) {
		return this.userRepository.findByPublicId(publicId);
	}
}
