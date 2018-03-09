package com.lms.entities;


import com.lms.enums.PriorityColor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class SystemAnnouncement extends BaseEntity {


    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private PriorityColor priorityColor;

    @OneToMany(mappedBy = "systemAnnouncement")
    private List<SystemResource> resources;



}
