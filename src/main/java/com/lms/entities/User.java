package com.lms.entities;

import com.lms.entities.course.Course;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

    //@Column(unique = true)
    private String username;

    //@Column(unique = true)
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

}