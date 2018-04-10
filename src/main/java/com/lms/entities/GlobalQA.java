package com.lms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "g_qas")
@Data
public class GlobalQA extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "qa")
    private List<GlobalQAComment> comments;

    @ManyToMany
    private List<GlobalQATag> tags;

    @OneToOne
    private GlobalQA parent;

    private boolean answer;

    private boolean anonymous;




}

