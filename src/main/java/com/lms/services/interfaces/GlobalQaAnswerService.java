package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.GlobalQaAnswer;
import com.lms.pojos.GlobalQaAnswerPojo;

import java.util.List;

public interface GlobalQaAnswerService {

    GlobalQaAnswerPojo entityToPojo(GlobalQaAnswer entity);

    GlobalQaAnswer pojoToEntity(GlobalQaAnswerPojo pojo);

    List<GlobalQaAnswerPojo> getQuestionAnswersByQuestionPublicKey(String questionPublicKey) throws DataNotFoundException;


}
