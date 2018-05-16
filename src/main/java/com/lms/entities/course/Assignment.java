package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */

@Entity
@Table(name="assignments")
@Data
public class Assignment extends BaseEntity {

    private String title;

    private String content;

    private String originalFileName;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "courseAssignment")
    private List<CourseResource> courseResources;

    @OneToMany(mappedBy = "assignment")
    private List<StudentAssignment> studentAssignments;

    @OneToOne
    private Grade grade;


}
