package com.lms.pojos.global;

import com.lms.pojos.BasePojo;
import com.lms.properties.PriorityColor;

import java.util.List;

public class SystemAnnouncementPojo extends BasePojo {

    private String title;

    private String content;

    private PriorityColor priorityColor;

    private List<SystemResourcePojo> resources;

    private List<String> imagePublicKeys;

    private List<String> resourceKeys;



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

    public List<String> getImagePublicKeys() {
        return imagePublicKeys;
    }

    public void setImagePublicKeys(List<String> imagePublicKeys) {
        this.imagePublicKeys = imagePublicKeys;
    }

    public List<String> getResourceKeys() {
        return resourceKeys;
    }

    public void setResourceKeys(List<String> resourceKeys) {
        this.resourceKeys = resourceKeys;
    }
}
