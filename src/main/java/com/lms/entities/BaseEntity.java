package com.lms.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Created by umit.kas on 05.12.2017.
 */
@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(unique = true)
    private String publicKey;


    @CreationTimestamp
    private Date createdAt;

    @OneToOne
    private User createdBy;


    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne
    private User updatedBy;


    private boolean visible = true;


    public void generatePublicKey(){
        this.publicKey = UUID.randomUUID().toString();
    }
}
