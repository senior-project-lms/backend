package com.lms.pojos;

import lombok.Data;

import java.util.List;

@Data
public class DefaultAuthorityPrivilegePojo extends BasePojo {

    private AuthorityPojo authority;

    private List<PrivilegePojo> privileges;
}
