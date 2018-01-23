package com.lms.entities.course;

import com.lms.entities.BaseEntity;


import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */

@Entity
@Table(name="assignments")
public class Assignment extends BaseEntity {

    private String name;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<CourseResource> courseResources;

    private String content;

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

    public List<CourseResource> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
