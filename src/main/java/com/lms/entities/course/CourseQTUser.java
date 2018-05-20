package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class CourseQTUser extends BaseEntity {


    @OneToOne
    private Course course;

    @OneToOne
    private CourseQuizTest qt;

    @OneToMany(mappedBy = "user")
    private List<CourseQTUserAnswer> answers;

    private Date startedAt;

    private boolean started;

    private Date finishesAt;

    private boolean finished;

    private Date finishedAt;

}
