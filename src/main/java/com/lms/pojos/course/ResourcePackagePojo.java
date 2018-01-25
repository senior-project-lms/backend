package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class ResourcePackagePojo extends BasePojo{


    private String name;


    private List<CourseResourcePojo> courseResources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseResourcePojo> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResourcePojo> courseResources) {
        this.courseResources = courseResources;
    }
}
