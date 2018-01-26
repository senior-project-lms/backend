package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */

@Entity
@Table(name="assignments")
@Data
public class Assignment extends BaseEntity {

    private String name;

    @ManyToOne
    private Course course;

    @OneToMany
    private List<CourseResource> courseResources;

    private String content;


}
