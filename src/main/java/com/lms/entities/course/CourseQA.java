package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "course_qas")
@Data
public class CourseQA extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "qa")
    private List<CourseQAComment> comments;


    @ManyToMany
    private List<CourseQATag> tags;


    @OneToOne
    private CourseQA parent;

    private boolean answer;

    private boolean anonymous;


}
