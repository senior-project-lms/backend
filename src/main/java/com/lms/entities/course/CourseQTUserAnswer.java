package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_question_user_answers")
@Data
public class CourseQTUserAnswer extends BaseEntity {

    @OneToOne
    private CourseQuizTest qt;

    @OneToOne
    private CourseQTQuestion question;
//
//    @OneToOne
//    private CourseQTAnswer answer;


    @ManyToMany
    private List<CourseQTAnswer> answers;

    @Column(columnDefinition = "TEXT")
    private String text;


    @ManyToOne
    private CourseQTUser user;

}
