package com.lms.pojos.course;

import com.lms.pojos.BasePojo;
import com.lms.pojos.course.CourseQTAnswerPojo;
import com.lms.pojos.course.CourseQTQuestionPojo;
import lombok.Data;

import java.util.List;

@Data
public class CourseQTUserAnswerPojo extends BasePojo {

    private String questionPublicKey;

    private List<String> answerPublicKeys;

    private String text;

}
