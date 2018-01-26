package com.lms.pojos.global;

import com.lms.pojos.BasePojo;
import com.lms.properties.PriorityColor;
import lombok.Data;

import java.util.List;

@Data
public class SystemAnnouncementPojo extends BasePojo {

    private String title;

    private String content;

    private PriorityColor priorityColor;

    private List<SystemResourcePojo> resources;

    private List<String> imagePublicKeys;

    private List<String> resourceKeys;


}
