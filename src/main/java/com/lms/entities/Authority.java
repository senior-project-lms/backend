package com.lms.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="authorities")
public class Authority extends BaseEntity {


    private String name;

    private int acessLevel;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


	public int getAcessLevel() {
		return acessLevel;
	}



	public void setAcessLevel(int acessLevel) {
		this.acessLevel = acessLevel;
	}


    
}