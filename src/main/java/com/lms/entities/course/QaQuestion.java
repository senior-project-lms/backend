package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qa_questions")
@Data
public class QaQuestion extends BaseEntity {

    private String title;

    private String content;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "question")
    private List<QaAnswer> answers;

    private boolean anonymous;


}
