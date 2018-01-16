package com.lms.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by umit.kas on 05.12.2017.
 */

@Entity
@Table(name = "resources")
public class Resource extends BaseEntity{


    private String name;

    @ManyToOne
    private Course course;

    @ManyToOne
    private ResourcePackage resourcePackage;

    @ManyToOne
    private Announcement announcement;

    @UpdateTimestamp
    private Date deletedAt;

    @OneToOne
    private User deleteddBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ResourcePackage getResourcePackage() {
        return resourcePackage;
    }

    public void setResourcePackage(ResourcePackage resourcePackage) {
        this.resourcePackage = resourcePackage;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public User getDeleteddBy() {
        return deleteddBy;
    }

    public void setDeleteddBy(User deleteddBy) {
        this.deleteddBy = deleteddBy;
    }
}