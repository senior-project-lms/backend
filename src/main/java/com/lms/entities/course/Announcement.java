package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.properties.PriorityColor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "announcements")
public class Announcement extends BaseEntity {


    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private PriorityColor priorityColor;

    @ManyToOne
    private Course course;

    private int priority;

    @OneToMany(mappedBy = "announcement")
    private List<CourseResource> courseResources;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<CourseResource> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }
}
