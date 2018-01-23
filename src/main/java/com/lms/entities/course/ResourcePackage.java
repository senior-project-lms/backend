package com.lms.entities.course;

import com.lms.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "resource_packages")
public class ResourcePackage extends BaseEntity {


    private String name;

    @OneToMany(mappedBy = "resourcePackage")
    private List<CourseResource> courseResources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseResource> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResource> courseResources) {
        this.courseResources = courseResources;
    }
}
