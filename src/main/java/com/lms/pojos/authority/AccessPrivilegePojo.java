package com.lms.pojos.authority;


import com.lms.pojos.BasePojo;
import com.lms.pojos.user.UserPojo;
import lombok.Data;

import java.util.List;

@Data
public class AccessPrivilegePojo extends BasePojo {

    private UserPojo user;

    private List<PrivilegePojo> privileges;

}
