package com.lms.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lms.enums.PriorityColor;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemAnnouncementPojo extends BasePojo {

    private String title;

    private String content;

    private PriorityColor priorityColor;

    private List<SystemResourcePojo> resources;

    private List<String> imagePublicKeys;

    private List<String> resourceKeys;


}
