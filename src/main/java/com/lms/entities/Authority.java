package com.lms.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;
    
    private String name;
    
   private int acessLevel;
   
   
    
       
    public Authority() {}



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public int getAcessLevel() {
		return acessLevel;
	}



	public void setAcessLevel(int acessLevel) {
		this.acessLevel = acessLevel;
	}


    
}