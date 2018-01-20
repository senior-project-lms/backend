package com.lms.repositories;

import com.lms.entities.Course;
import com.lms.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by umit.kas on 28.11.2017.
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>{

}
