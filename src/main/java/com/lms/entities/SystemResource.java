package com.lms.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SystemResource extends BaseEntity{


    private String name;

    private String path;

    private String type;

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
}
