package com.lms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "g_qa_answers")
@Data

public class GlobalQaAnswer extends BaseEntity{

    private String content;

    @ManyToOne
    private GlobalQaQuestion question;

    private boolean verified;

    @OneToMany
    @JoinTable(
            name = "answer_subanswer",
            joinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subanswer_id", referencedColumnName = "id")

    )
    private List<GlobalQaAnswer> subanswers;

    @OneToOne
    private User verifiedBy;

}