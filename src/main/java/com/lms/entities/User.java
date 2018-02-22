package com.lms.entities;

import com.lms.entities.course.Course;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String name;

    private String surname;
    @JsonIgnore
    private String password;


    private boolean enabled = true;

    private boolean blocked;
    
    @ManyToOne
    private Authority authority;


    @ManyToMany(mappedBy = "registeredUsers")
	private List<Course> registeredCoursesAsStudent;

    @OneToMany(mappedBy = "owner")
	private List<Course> ownedCourses;


    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    @JoinTable(
            name = "user_access_privileges",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    )
    private List<Privilege> accessPrivileges;



}