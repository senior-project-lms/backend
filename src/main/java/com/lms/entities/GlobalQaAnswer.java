package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "g_qa_answers")
@Data

public class GlobalQaAnswer extends BaseEntity{

    private String content;

    @ManyToOne
    private GlobalQaQuestion question;

    private boolean verified;

    @OneToOne
    private User verifiedBy;

}