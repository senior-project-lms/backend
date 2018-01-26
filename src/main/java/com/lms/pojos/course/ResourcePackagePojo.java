package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import lombok.Data;

import java.util.List;

@Data
public class ResourcePackagePojo extends BasePojo{


    private String name;


    private List<CourseResourcePojo> courseResources;


}
