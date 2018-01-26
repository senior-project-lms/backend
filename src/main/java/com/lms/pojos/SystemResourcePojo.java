package com.lms.pojos;

import lombok.Data;

@Data
public class SystemResourcePojo extends BasePojo {

    private String name;

    private String path;

    private String type;

    private SystemAnnouncementPojo announcment;

    private String originalFileName;

    private String url;
}
