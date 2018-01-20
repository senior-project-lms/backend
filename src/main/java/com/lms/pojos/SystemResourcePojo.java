package com.lms.pojos;

public class SystemResourcePojo extends BasePojo{

    private String name;

    private String path;

    private String type;

    private SystemAnnouncementPojo announcment;


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


    public SystemAnnouncementPojo getAnnouncment() {
        return announcment;
    }

    public void setAnnouncment(SystemAnnouncementPojo announcment) {
        this.announcment = announcment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
