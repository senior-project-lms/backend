package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivilegePojo extends BasePojo {

    private long code;

    private String name;

}
