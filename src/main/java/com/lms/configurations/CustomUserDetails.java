package com.lms.configurations;

import com.lms.entities.Authority;
import com.lms.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Provides a basic implementation of the UserDetails interface
 */
public class CustomUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
		this.authorities = translate(Arrays.asList( user.getAuthority()));
		
    }

    private Collection<? extends GrantedAuthority> translate(List<Authority> roles) {
        List<GrantedAuthority> authorities = new ArrayList();
        for (Authority role : roles) {
            String level = Integer.toString(role.getAcessLevel());
            //Make sure that all roles start with "ROLE_"
            level = "ROLE_" + level;
            authorities.add(new SimpleGrantedAuthority(level));
        }
        return authorities;
    }

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUsername();
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !this.user.isBlocked();
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.isEnabled();
	}

  
}