package com.lms.pojos;

import com.lms.enums.PriorityColor;
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
