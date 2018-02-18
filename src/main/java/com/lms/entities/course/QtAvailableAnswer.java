package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.enums.EAnswerChoice;
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
public class QtAvailableAnswer extends BaseEntity {

    private String text;

    private EAnswerChoice type;

    private boolean correct;

    @ManyToOne
    private QtQuestion question;

}
