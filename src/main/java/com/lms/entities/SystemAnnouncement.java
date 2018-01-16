package com.lms.entities;


import com.lms.properties.PriorityColor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class SystemAnnouncement extends BaseEntity{


    private String title;

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
