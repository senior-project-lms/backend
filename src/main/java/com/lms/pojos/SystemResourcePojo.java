package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemResourcePojo extends BasePojo {

    private String name;

    private String path;

    private String type;

    private SystemAnnouncementPojo announcment;

    private String originalFileName;

    private String url;
}
