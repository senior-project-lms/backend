package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "g_qa_questions")
@Data
public class GlobalQaQuestion extends BaseEntity {

    private String title;

    private String content;

    @OneToMany(mappedBy = "question")
    private List<GlobalQaAnswer> answers;

    private boolean anonymous;



}

