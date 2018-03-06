package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.course.QaAnswer;
import com.lms.pojos.course.QaAnswerPojo;

import java.util.List;

public interface QaAnswerService {

    QaAnswerPojo entityToPojo(QaAnswer entity);

    QaAnswer pojoToEntity(QaAnswerPojo pojo);

    List<QaAnswerPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException;
}
