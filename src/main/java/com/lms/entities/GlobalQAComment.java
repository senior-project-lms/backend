package com.lms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "g_qa_comments")
@Data

public class GlobalQAComment extends BaseEntity{

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private GlobalQA qa;


    private boolean anonymous;
}