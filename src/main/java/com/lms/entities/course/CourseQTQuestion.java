package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_questions")
@Data
public class CourseQTQuestion extends BaseEntity {

    private int order;

    private String content;

    @OneToMany
    private List<CourseQTAnswer> answers;


    @ManyToOne
    private CourseQuizTest quizTest;



}