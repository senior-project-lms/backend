package com.lms.pojos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BasePojo implements Serializable {

    private String publicKey;


    private Date createdAt;

    private UserPojo createdBy;


    private Date updatedAt;

    private UserPojo updatedBy;
}
