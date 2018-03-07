package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qa_answers")
@Data
public class QaAnswer extends BaseEntity {


    private String content;

    @ManyToOne
    private QaQuestion question;

    private boolean verified;

    @OneToOne
    private User verifiedBy;

    @OneToMany
    @JoinTable(
            name = "answer_subanswer",
            joinColumns = @JoinColumn(name = "answer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subanswer_id", referencedColumnName = "id")

    )
    private List<QaAnswer> subanswers;

}