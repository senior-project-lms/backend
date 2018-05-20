package com.lms.entities.course;


import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="student_assignment")
@Data
public class StudentAssignment extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    Assignment assignment;

    @OneToMany(mappedBy = "studentAssignment")
    private List<CourseResource> courseResources;



}
