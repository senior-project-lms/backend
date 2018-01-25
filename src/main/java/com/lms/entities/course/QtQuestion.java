package com.lms.entities.course;

import com.lms.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_questions")
public class QtQuestion extends BaseEntity {

    private String title;

    private String content;

    @OneToMany
    private List<QtAvailableAnswer> answers;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QtAvailableAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QtAvailableAnswer> answers) {
        this.answers = answers;
    }

}
