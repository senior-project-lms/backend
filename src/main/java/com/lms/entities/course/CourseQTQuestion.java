package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_questions")
@Data
public class CourseQTQuestion extends BaseEntity {

    @Column(name = "order_index")
    private int order;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "question")
    private List<CourseQTAnswer> answers;


    @ManyToOne
    private CourseQuizTest quizTest;



}
