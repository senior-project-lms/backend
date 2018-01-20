package com.lms.pojos;

import com.lms.entities.SystemResource;
import com.lms.properties.PriorityColor;

import java.io.Serializable;
import java.util.List;

public class SystemAnnouncementPojo extends BasePojo {

    private String title;

    private String content;

    private PriorityColor priorityColor;

    private List<SystemResourcePojo> resources;

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

    public List<SystemResourcePojo> getResources() {
        return resources;
    }

    public void setResources(List<SystemResourcePojo> resources) {
        this.resources = resources;
    }
}
