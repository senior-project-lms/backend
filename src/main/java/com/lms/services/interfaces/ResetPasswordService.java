package com.lms.services.interfaces;
import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.PasswordResetToken;
import com.lms.entities.User;
import com.lms.pojos.PasswordResetDto;
import com.lms.pojos.ResetPasswordPojo;
import com.lms.pojos.UserPojo;

public interface ResetPasswordService {

    ResetPasswordPojo entityToPojo(PasswordResetToken entity) throws DataNotFoundException, ExecutionFailException;

    PasswordResetToken pojoToEntity(ResetPasswordPojo pojo) throws DataNotFoundException;


    PasswordResetToken getPasswordResetToken(String token);

    void createPasswordResetTokenForUser(String email) throws ExecutionFailException, DataNotFoundException, InterruptedException;

    boolean updatePassword(String token, PasswordResetDto pojo) throws DataNotFoundException, ExecutionFailException;
}
