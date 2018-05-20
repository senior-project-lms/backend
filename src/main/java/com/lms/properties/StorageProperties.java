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


    @Value("${storage.systemAnnouncement.file.path}")
    private String systemAnnouncementFilePath;


    @Value("${storage.course.file.path}")
    private String courseFilePath;


    public List<String> getAllPaths(){
        return new ArrayList<>(Arrays.
                asList(rootPath,
                        systemAnnouncementFilePath,
                        courseFilePath));
    }


    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }



    public String getSystemAnnouncementFilePath() {
        return systemAnnouncementFilePath;
    }

    public void setSystemAnnouncementFilePath(String systemAnnouncementFilePath) {
        this.systemAnnouncementFilePath = systemAnnouncementFilePath;
    }

    public String getCourseFilePath(String coursePublicKey) {
        return String.format("%s/%s", courseFilePath, coursePublicKey);
    }

    public void setCourseFilePath(String courseFilePath) {
        this.courseFilePath = courseFilePath;
    }
}
