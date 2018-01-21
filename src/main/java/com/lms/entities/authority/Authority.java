package com.lms.entities.authority;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lms.entities.BaseEntity;
import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="authorities")
public class Authority extends BaseEntity {


    private String name;

    private long accessLevel;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(long accessLevel) {
        this.accessLevel = accessLevel;
    }
}