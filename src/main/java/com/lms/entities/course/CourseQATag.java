package com.lms.entities.course;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "course_qa_tags")
public class CourseQATag extends BaseEntity {


    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<CourseQA> qas;
}
