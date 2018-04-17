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
public class CourseQuizTest extends BaseEntity {

    private String name;

    private String detail;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<CourseQTQuestion> questions;

    private Date startAt;

    private Date endAt;

    private boolean limitedDuration;

    private boolean hasDueDate;

    private boolean gradable;

    private boolean published;

}
