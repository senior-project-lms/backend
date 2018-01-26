package com.lms.entities.user;

import com.lms.entities.BaseEntity;
import com.lms.entities.authority.Authority;
import com.lms.entities.course.Course;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

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

}