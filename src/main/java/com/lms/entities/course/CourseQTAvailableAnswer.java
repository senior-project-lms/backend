package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_available_answers")
@Data
public class CourseQTAvailableAnswer extends BaseEntity {

    private String text;

    private int type;

    private boolean correct;

    @ManyToOne
    private CourseQTQuestion question;

}
