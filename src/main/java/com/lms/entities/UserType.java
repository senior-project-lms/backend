package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "user_types")
@Data
public class UserType extends BaseEntity {

    private String name;
}
