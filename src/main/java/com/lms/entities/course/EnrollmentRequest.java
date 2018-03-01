package com.lms.entities.course;


import com.lms.entities.BaseEntity;
import com.lms.entities.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "course_enrollment_request")
public class EnrollmentRequest extends BaseEntity {

    @OneToOne
    private Course course;

    @OneToOne
    private User user;

    private boolean cancelled;

    private boolean rejected;

    private boolean enrolled;

    private boolean pending;
}
