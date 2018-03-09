package com.lms.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
@Data
public class Authority extends BaseEntity {


    private String name;

    @Column(unique = true)
    private long code;


}