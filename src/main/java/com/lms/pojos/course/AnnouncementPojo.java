package com.lms.pojos.course;

import com.lms.entities.BaseEntity;
import com.lms.enums.PriorityColor;
import lombok.Data;

import java.util.List;

@Data
public class AnnouncementPojo extends BaseEntity{

    private String title;

    private String content;

    private PriorityColor priorityColor;


    private CoursePojo course;

    private int priority;

    private List<CourseResourcePojo> courseResources;


}
