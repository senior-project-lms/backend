package com.lms.services.interfaces.course;

import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQATag;
import com.lms.entities.course.CourseQATag;
import com.lms.pojos.GlobalQATagPojo;
import com.lms.pojos.course.CourseQATagPojo;

import java.util.List;

public interface CourseQATagService {


    CourseQATagPojo entityToPojo(CourseQATag entity);

    CourseQATag pojoToEntity(CourseQATagPojo pojo);

    List<CourseQATag> save(List<CourseQATagPojo> pojos) throws ExecutionFailException, EmptyFieldException;

    List<CourseQATag> findAllByPublicKeys(List<String> publicKeys);

    List<CourseQATagPojo> searchByName(String name);


}
