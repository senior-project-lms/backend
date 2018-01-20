package com.lms.pojos;

import java.io.Serializable;
import java.util.Date;

public class BasePojo implements Serializable {

    private String publicKey;


    private Date createdAt;

    private UserPojo createdBy;


    private Date updatedAt;

    private UserPojo updatedBy;


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserPojo getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserPojo createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserPojo getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserPojo updatedBy) {
        this.updatedBy = updatedBy;
    }
}
