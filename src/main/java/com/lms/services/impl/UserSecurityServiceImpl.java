package com.lms.services.impl;

import com.lms.entities.PasswordResetToken;
import com.lms.entities.User;
import com.lms.repositories.PasswordResetTokenRepository;
import com.lms.services.interfaces.UserSecurityService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;

@Service
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService {

    /*
    if the token is valid, the user can authorize to change the password by granting them a CHANGE_PASSWORD_PRIVILEGE, and direct them to a page to update their password.

     */
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Override
    public String validatePasswordResetToken(long id, String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getId() != id)) {
            return "invalidToken";
        }
        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiresAt().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }
        User user = passToken.getUser();
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

}


