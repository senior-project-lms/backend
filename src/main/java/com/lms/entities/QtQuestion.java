package com.lms.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(name = "qt_questions")
public class QtQuestion extends BaseEntity{

    private String title;

    private String content;

    @OneToMany
    private List<QtAvailableAnswers> answers;

    private float weight;


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

    public List<QtAvailableAnswers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QtAvailableAnswers> answers) {
        this.answers = answers;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
