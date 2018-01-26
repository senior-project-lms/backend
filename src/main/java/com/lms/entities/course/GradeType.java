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
@Entity
@Table(name = "grade_types")
@Data
public class GradeType extends BaseEntity {


    private String name;

    private  float weight;

    @OneToMany
    private List<Grade> grades;

    @ManyToOne
    private  Course course;

}
