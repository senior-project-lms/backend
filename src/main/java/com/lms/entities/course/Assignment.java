package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */

@Entity
@Table(name="assignments")
@Data
public class Assignment extends BaseEntity {

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    private Date dueDate;

    private Date lastDate;



    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "courseAssignment")
    private List<CourseResource> courseResources;

    @OneToMany(mappedBy = "assignment")
    private List<StudentAssignment> studentAssignments;

    private boolean gradable;

    @OneToOne
    private Grade grade;

    private boolean published;


}
