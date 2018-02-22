package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultAuthorityPrivilegePojo extends BasePojo {

    private AuthorityPojo authority;

    //private List<PrivilegePojo> privileges;

    private List<String> privileges;
}
