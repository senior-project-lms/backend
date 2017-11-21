package com.lms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    @JsonIgnore
    @Column(name="id", insertable=false, updatable=false)
    private Long id;
    
    @JsonProperty("public_id")
    private String publicId;

    private String username;
	private String email;

	@JsonIgnore
    private String name;
    @JsonIgnore
    private String surname;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private boolean blocked;
    
    @ManyToOne
    @JsonIgnore
    private Authority authority;


    public User() {
    }


    public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

	public Authority getAuthority() {
		return authority;
	}


	public void setAuthority(Authority authority) {
		this.authority = authority;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.getUsername(), this.getPassword());
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name.toUpperCase();
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname.toUpperCase();
	}


	public String getPublicId() {
		return publicId;
	}


	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
	
	@Override
	public boolean equals(Object obj) {
		User other = (User) obj;
		return this.getId() == other.getId();
	}
	
}