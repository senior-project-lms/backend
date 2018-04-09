package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.GlobalQA;
import com.lms.entities.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "course_qa_comments")
@Data
public class QAComment extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private QA qa;


    private boolean anonymous;

}