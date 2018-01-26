package com.lms.entities.authority;

import com.lms.entities.BaseEntity;
import com.lms.entities.course.Course;
import com.lms.entities.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by umit.kas on 28.11.2017.
 */
@Entity
@Table(name = "user_course_root_privalage")
@Data
public class UserCoursePrivilege extends BaseEntity {


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

}

