package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.global.SystemAnnouncementPojo;
import com.lms.pojos.user.UserPojo;

import java.util.Date;

public class CourseResourcePojo extends BasePojo {


    private String name;

    private String path;

    private String type;

    private CoursePojo course;

    private ResourcePackagePojo resourcePackage;

    private SystemAnnouncementPojo announcement;

    private Date deletedAt;


    private UserPojo deletedBy;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }

    public ResourcePackagePojo getResourcePackage() {
        return resourcePackage;
    }

    public void setResourcePackage(ResourcePackagePojo resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    public SystemAnnouncementPojo getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(SystemAnnouncementPojo announcement) {
        this.announcement = announcement;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UserPojo getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(UserPojo deletedBy) {
        this.deletedBy = deletedBy;
    }
}
