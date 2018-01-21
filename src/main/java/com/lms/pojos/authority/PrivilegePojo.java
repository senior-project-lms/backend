package com.lms.pojos.authority;

import com.lms.pojos.BasePojo;

public class PrivilegePojo extends BasePojo {

    private long code;

    private String name;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
