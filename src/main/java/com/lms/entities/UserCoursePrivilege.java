package com.lms.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Entity
@Table(name = "user_course_root_privalage")
public class UserCoursePrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private User user;

    @ManyToOne
    private Course course;

    @ManyToMany
    @JoinTable(
            name = "user_course_privilege",
            joinColumns = @JoinColumn(
                    name = "user_course_privilege_id", referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")

    )
    private List<Privilege> privileges;

    private boolean visible;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}

