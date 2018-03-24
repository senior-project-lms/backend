package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity(name = "calendar")
@Data
public class Event extends BaseEntity {

    @ManyToOne
    private Course course;

    private String title;

    private Date start;

    private Date end;

    @OneToOne
    private Announcement announcement;

}
