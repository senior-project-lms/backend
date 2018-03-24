package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "system_events")
@Data
public class SystemEvent extends BaseEntity {

    private String title;

    private Date start;

    private Date end;

    @OneToOne
    private SystemAnnouncement announcement;

}
