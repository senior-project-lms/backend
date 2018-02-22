package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorityPojo extends BasePojo {


    private String name;

    private Long code;

}
