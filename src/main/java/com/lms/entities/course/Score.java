package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Entity
@Table(name = "scores")
@Data
public class Score extends BaseEntity {


    @ManyToOne
    private Grade grade;

    @OneToOne
    private User student;

    private float score;


}
