package com.lms.pojos;

public class AuthorityPojo extends BasePojo {


    private String name;

    private Long accessLevel;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Long accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public boolean equals(Object o) {
        BasePojo object = (BasePojo) o;
        return getPublicKey().matches(object.getPublicKey());
    }
}
