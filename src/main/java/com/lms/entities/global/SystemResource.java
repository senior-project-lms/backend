package com.lms.entities.global;


import com.lms.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SystemResource extends BaseEntity {


    private String name;

    private String path;

    private String type;

    private String originalFileName;

    private String url;

    @ManyToOne
    private SystemAnnouncement systemAnnouncement;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SystemAnnouncement getSystemAnnouncement() {
        return systemAnnouncement;
    }

    public void setSystemAnnouncement(SystemAnnouncement systemAnnouncement) {
        this.systemAnnouncement = systemAnnouncement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
