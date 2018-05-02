package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Entity(name = "grades")
@Data
public class Grade extends BaseEntity {

    private String name;

    private float maxScore;

    private float weight;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "grade")
    private List<Score> scores;


    private boolean published;
}
