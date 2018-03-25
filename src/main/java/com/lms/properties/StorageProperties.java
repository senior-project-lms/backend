package com.lms.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ConfigurationProperties
public class StorageProperties {

    @Value("${storage.rootPath}")
    private String rootPath;

    @Value("${storage.systemAnnouncement.image.path}")
    private String systemAnnouncementImagePath;

    @Value("${storage.systemAnnouncement.file.path}")
    private String systemAnnouncementFilePath;

    @Value("${storage.course.image.path}")
    private String courseImagePath;

    @Value("${storage.course.file.path}")
    private String courseFilePath;


    public List<String> getAllPaths(){
        return new ArrayList<>(Arrays.
                asList(rootPath,
                        systemAnnouncementImagePath,
                        systemAnnouncementFilePath,
                        courseImagePath,
                        courseFilePath));
    }


    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getSystemAnnouncementImagePath() {
        return systemAnnouncementImagePath;
    }

    public void setSystemAnnouncementImagePath(String systemAnnouncementImagePath) {
        this.systemAnnouncementImagePath = systemAnnouncementImagePath;
    }

    public String getSystemAnnouncementFilePath() {
        return systemAnnouncementFilePath;
    }

    public void setSystemAnnouncementFilePath(String systemAnnouncementFilePath) {
        this.systemAnnouncementFilePath = systemAnnouncementFilePath;
    }

    public String getCourseImagePath() {
        return courseImagePath;
    }

    public void setCourseImagePath(String courseImagePath) {
        this.courseImagePath = courseImagePath;
    }

    public String getCourseFilePath() {
        return courseFilePath;
    }

    public void setCourseFilePath(String courseFilePath) {
        this.courseFilePath = courseFilePath;
    }
}
