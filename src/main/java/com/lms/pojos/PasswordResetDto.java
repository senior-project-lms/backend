package com.lms.pojos;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class PasswordResetDto {

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;


}