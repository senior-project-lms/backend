package com.lms.services.interfaces.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.QaComment;
import com.lms.pojos.course.QaCommentPojo;

import java.util.List;

public interface QaAnswerService {

    QaCommentPojo entityToPojo(QaComment entity);

    QaComment pojoToEntity(QaCommentPojo pojo);

    List<QaCommentPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException;
}
