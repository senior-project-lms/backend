package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "course_quiz_tests")
@Data
public class QuizTest extends BaseEntity {

    private String name;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<QtQuestion> questions;

    private Date startAt;

    private Date endAt;

}
