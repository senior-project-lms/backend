package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "g_qa_votes")
@Data
public class GlobalQAVote extends BaseEntity{

    private boolean up;
    private boolean down;
    private boolean star;

    @OneToOne
    private GlobalQA qa;

}
