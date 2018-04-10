package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "g_qa_votes")
@Data
public class CourseQAVote extends BaseEntity {

    private boolean up;
    private boolean down;
    private boolean star;

    @OneToOne
    private CourseQA qa;

}
