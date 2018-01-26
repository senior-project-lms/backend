package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_question_user_answers")
@Data
public class QtQuestionUserAnswer extends BaseEntity {

    @OneToOne
    private QtQuestion question;

    @OneToOne
    private QtAvailableAnswer answer;



}
