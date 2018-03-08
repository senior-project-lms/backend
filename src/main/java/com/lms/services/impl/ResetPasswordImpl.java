package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.*;
import com.lms.pojos.MailPojo;
import com.lms.pojos.PasswordResetDto;
import com.lms.pojos.ResetPasswordPojo;
import com.lms.pojos.UserPojo;
import com.lms.repositories.PasswordResetTokenRepository;
import com.lms.services.interfaces.MailService;
import com.lms.services.interfaces.ResetPasswordService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class ResetPasswordImpl implements ResetPasswordService {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResetPasswordPojo entityToPojo(PasswordResetToken entity) throws DataNotFoundException, ExecutionFailException {
        ResetPasswordPojo pojo = new ResetPasswordPojo();
        pojo.setEmail(entity.getUser().getEmail());
        return pojo;
    }

    @Override
    public PasswordResetToken pojoToEntity(ResetPasswordPojo pojo) {
        PasswordResetToken entity = new PasswordResetToken();
        entity.setToken(pojo.getToken());
        return entity;

    }


    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(String email) throws ExecutionFailException, DataNotFoundException, InterruptedException {


        if (!userService.emailExist(email)) {
            throw new DataNotFoundException("No such a user profile is found");
        }

        User user = userService.findByEmail(email);
        String token = "";
        for (int i = 0; i < 3; i++) {
            token += UUID.randomUUID().toString();
        }
        token = token.replaceAll("[^a-zA-Z0-9]", "");


        PasswordResetToken myToken = new PasswordResetToken();
        myToken.generateExpiredDate(60);
        myToken.generatePublicKey();
        myToken.setUser(user);
        myToken.setToken(token);
        myToken = passwordTokenRepository.save(myToken);
        if (myToken == null || myToken.getId() == 0) {
            throw new DataNotFoundException("No such a user profile is found");

        }
        String hashedPassword = passwordEncoder.encode(email);
        hashedPassword = hashedPassword.replaceAll("[^a-zA-Z0-9]", "");

        token += "-" + hashedPassword;

        MailPojo pojo = new MailPojo();
        pojo.setTo(Arrays.asList(email));
        pojo.setSubject("Reset Password");
        pojo.setText(String.format("http://localhost:8080/reset-password/%s", token));
        mailService.send(pojo);


    }

    @Override
    public boolean updatePassword(String token, PasswordResetDto pojo) throws DataNotFoundException, ExecutionFailException {

        String[] tokens = token.split("-");
        token = tokens[0];
        String hashedEmail = tokens[1];


        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if (passToken == null) {
            throw new DataNotFoundException("Invalid Token");

        }
        String realHashedEmail = passwordEncoder.encode(passToken.getUser().getEmail());
        realHashedEmail = realHashedEmail.replaceAll("[^a-zA-Z0-9]", "");
        if (passToken == null && realHashedEmail.equals(hashedEmail)) {
            throw new DataNotFoundException("Invalid Token");
        }
        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiresAt().getTime() - cal.getTime().getTime()) <= 0) {
            throw new DataNotFoundException("Expired");

        }
        userService.updatePassword(passToken.getUser(), pojo.getPassword());
        return true;
    }


}