package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "course_qa_comments")
@Data
public class CourseQAComment extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private CourseQA qa;


    private boolean anonymous;

}