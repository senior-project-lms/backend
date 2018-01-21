package com.lms.entities.global;


import com.lms.entities.BaseEntity;
import com.lms.properties.PriorityColor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class SystemAnnouncement extends BaseEntity {


    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private PriorityColor priorityColor;

    @OneToMany(mappedBy = "systemAnnouncement")
    private List<SystemResource> resources;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PriorityColor getPriorityColor() {
        return priorityColor;
    }

    public void setPriorityColor(PriorityColor priorityColor) {
        this.priorityColor = priorityColor;
    }

    public List<SystemResource> getResources() {
        return resources;
    }

    public void setResources(List<SystemResource> resources) {
        this.resources = resources;
    }
}
