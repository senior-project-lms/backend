package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "qa_tags")
public class GlobalQATag extends BaseEntity {


    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<GlobalQA> qas;

}
