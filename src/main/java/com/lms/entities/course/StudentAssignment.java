package com.lms.entities.course;


import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="student_assignment")
@Data
public class StudentAssignment extends BaseEntity {

    private String content;

    @ManyToOne
    Assignment assignment;

    @OneToMany(mappedBy = "studentAssignment")
    private List<CourseResource> courseResources;



}
