package com.lms.configurations;

import com.lms.entities.Authority;
import com.lms.entities.Privilege;
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

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;


    }


    public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
        List<GrantedAuthority> privileges = new ArrayList();


        for (Privilege privilege : user.getAccessPrivileges()) {
            //Make sure that all roles start with "ROLE_"
            String role = String.format("ROLE_%d", privilege.getCode());
            privileges.add(new SimpleGrantedAuthority(role));
        }

        return privileges;
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