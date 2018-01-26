package com.lms.pojos.authority;

import com.lms.pojos.BasePojo;
import lombok.Data;

@Data
public class AuthorityPojo extends BasePojo {


    private String name;

    private Long accessLevel;

}
