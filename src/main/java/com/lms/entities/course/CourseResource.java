package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.User;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by umit.kas on 05.12.2017.
 */

@Entity
@Table(name = "resources")
@Data
public class CourseResource extends BaseEntity {


    private String name;

    private String path;

    private String type;

    private String originalFileName;

    private String url;

    @ManyToOne
    private Course course;

    @ManyToOne
    private ResourcePackage resourcePackage;

    @ManyToOne
    private Announcement announcement;

    @ManyToOne
    private Assignment courseAssignment;

    @ManyToOne
    private StudentAssignment studentAssignment;

    @UpdateTimestamp
    private Date deletedAt;

    @OneToOne
    private User deletedBy;


}
