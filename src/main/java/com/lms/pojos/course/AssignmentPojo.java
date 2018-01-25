package com.lms.pojos.course;

import com.lms.pojos.BasePojo;

import java.util.List;

public class AssignmentPojo extends BasePojo {

    private String name;

    private CoursePojo course;


    private List<CourseResourcePojo> courseResources;

    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoursePojo getCourse() {
        return course;
    }

    public void setCourse(CoursePojo course) {
        this.course = course;
    }

    public List<CourseResourcePojo> getCourseResources() {
        return courseResources;
    }

    public void setCourseResources(List<CourseResourcePojo> courseResources) {
        this.courseResources = courseResources;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
