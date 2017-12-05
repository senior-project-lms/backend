package com.lms.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_questions")
public class QtQuestion extends BaseEntity{

    private String title;

    private String content;

    @OneToMany
    private List<QtAvailableAnswers> answers;

    private float weight;

}
