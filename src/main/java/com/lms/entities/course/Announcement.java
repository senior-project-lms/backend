package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.properties.PriorityColor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "announcements")
@Data
public class Announcement extends BaseEntity {


    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private PriorityColor priorityColor;

    @ManyToOne
    private Course course;

    private int priority;

    @OneToMany(mappedBy = "announcement")
    private List<CourseResource> courseResources;

}
