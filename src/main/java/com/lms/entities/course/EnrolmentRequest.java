package com.lms.entities.course;


import com.lms.entities.BaseEntity;
import com.lms.entities.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "course_enrolment_request")
public class EnrolmentRequest extends BaseEntity {

    @OneToOne
    private Course course;

    @OneToOne
    private User user;


}
