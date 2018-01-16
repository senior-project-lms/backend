package com.lms.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SystemResource extends BaseEntity{


    private String name;

    private String path;

    @ManyToOne
    private SystemAnnouncement systemAnnouncement;


}
