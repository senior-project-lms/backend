package com.lms.properties.custom;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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


    public List<String> getAllPaths(){
        return new ArrayList<>(Arrays.
                asList(rootPath,
                        systemAnnouncementImagePath,
                        systemAnnouncementFilePath));
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




}
