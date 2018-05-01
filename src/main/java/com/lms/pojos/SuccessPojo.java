package com.lms.pojos;

import lombok.Data;
import lombok.Getter;

@Getter
public class SuccessPojo {

    private boolean status;
    private String publicKey;


    public SuccessPojo(String publicKey) {
        this.publicKey = publicKey;
        this.status = true;
    }
}
