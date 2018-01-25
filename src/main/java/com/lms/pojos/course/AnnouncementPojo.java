package com.lms.pojos.course;

import com.lms.entities.BaseEntity;
import com.lms.properties.PriorityColor;

import java.util.List;

public class AnnouncementPojo extends BaseEntity{

    private String title;

    private String content;

    private PriorityColor priorityColor;


    private CoursePojo course;

    private int priority;

    private List<CourseResourcePojo> courseResources;


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

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<CourseResourcePojo> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResourcePojo> courseResources) {
        this.courseResources = courseResources;
    }
}
