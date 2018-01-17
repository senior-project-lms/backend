package com.lms.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@Entity
@Table(name="users")
public class User extends BaseEntity{

    private String username;

    private String email;

    private String name;

    private String surname;

    private String password;

    private boolean enabled;

    private boolean blocked;
    
    @ManyToOne
    private Authority authority;


    @ManyToMany(mappedBy = "registeredUsers")
	private List<Course> registeredCoursesAsStudent;

    @OneToMany(mappedBy = "owner")
	private List<Course> ownedCourses;




	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public List<Course> getRegisteredCoursesAsStudent() {
		return registeredCoursesAsStudent;
	}

	public void setRegisteredCoursesAsStudent(List<Course> registeredCoursesAsStudent) {
		this.registeredCoursesAsStudent = registeredCoursesAsStudent;
	}

	public List<Course> getOwnedCourses() {
		return ownedCourses;
	}

	public void setOwnedCourses(List<Course> ownedCourses) {
		this.ownedCourses = ownedCourses;
	}
}