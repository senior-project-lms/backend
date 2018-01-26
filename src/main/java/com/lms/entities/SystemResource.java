package com.lms.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class SystemResource extends BaseEntity {


    private String name;

    private String path;

    private String type;

    private String originalFileName;

    private String url;

    @ManyToOne
    private SystemAnnouncement systemAnnouncement;


}
