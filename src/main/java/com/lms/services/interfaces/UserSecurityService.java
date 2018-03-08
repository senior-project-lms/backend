package com.lms.services.interfaces;

import com.lms.entities.PasswordResetToken;
import com.lms.entities.User;

public interface UserSecurityService {

    String validatePasswordResetToken(long id, String token);

}
