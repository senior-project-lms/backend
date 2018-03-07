package com.lms.pojos;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResetPasswordPojo extends BasePojo {

    private String token;
    private String password;
    private String newPassword;
    private String email;

}
