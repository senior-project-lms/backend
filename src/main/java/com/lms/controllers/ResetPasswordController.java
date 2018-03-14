package com.lms.controllers;


import com.lms.pojos.PasswordResetDto;
import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.UserPojo;
import com.lms.services.interfaces.MailService;
import com.lms.services.interfaces.ResetPasswordService;
import com.lms.services.interfaces.UserSecurityService;
import com.lms.services.interfaces.UserService;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;

import javax.validation.Valid;
import java.util.Locale;

@RestController
public class ResetPasswordController {


    @Autowired
    private ResetPasswordService resetPasswordService;




    @PostMapping(value = {"/forgot-password"} )
    @ResponseBody
    public void sendConformationMailTo(@RequestBody UserPojo userPojo) throws DataNotFoundException, ExecutionFailException, InterruptedException {
        resetPasswordService.createPasswordResetTokenForUser(userPojo.getEmail());


    }

    @PostMapping(value = {"/reset-password/{token}"})
    public void showChangePasswordPage(@PathVariable final String token, @RequestBody PasswordResetDto pojo) throws ExecutionFailException, DataNotFoundException {

        resetPasswordService.updatePassword(token, pojo);

    }
}


