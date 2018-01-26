package com.lms.pojos;


import lombok.Data;

import java.util.List;

@Data
public class AccessPrivilegePojo extends BasePojo {

    private UserPojo user;

    private List<PrivilegePojo> privileges;

}
