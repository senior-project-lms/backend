package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_answers")
@Data
public class CourseQTAnswer extends BaseEntity {

    private String text;

    private int type;

    private boolean correct;

    @ManyToOne
    private CourseQTQuestion question;


    @ManyToMany(mappedBy = "answers")
    private List<CourseQTUserAnswer> userAnswers;
}
