package com.lms.entities.authority;


import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="authorities")
@Data
public class Authority extends BaseEntity {


    private String name;

    private long accessLevel;


}