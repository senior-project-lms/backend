package com.lms.services.impl.course;

import com.lms.repositories.course.CourseRepository;
import com.lms.services.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;


}
