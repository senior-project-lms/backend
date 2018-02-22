package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasePojo implements Serializable {

    private String publicKey;


    private Date createdAt;

    private UserPojo createdBy;

    private Date updatedAt;

    private UserPojo updatedBy;

    private boolean visible;
}
