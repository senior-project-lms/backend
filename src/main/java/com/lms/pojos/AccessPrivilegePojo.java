package com.lms.pojos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessPrivilegePojo extends BasePojo {

    private UserPojo user;

    private List<PrivilegePojo> privileges;

}
