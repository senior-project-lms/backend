package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import com.lms.entities.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

}
