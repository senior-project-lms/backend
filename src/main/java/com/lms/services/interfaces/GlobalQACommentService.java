package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQAComment;
import com.lms.pojos.GlobalQACommentPojo;

import java.util.List;

public interface GlobalQACommentService {

    GlobalQACommentPojo entityToPojo(GlobalQAComment entity);

    GlobalQAComment pojoToEntity(GlobalQACommentPojo pojo);

    boolean save(String qaPublicKey, GlobalQACommentPojo pojo) throws DataNotFoundException, ExecutionFailException;


}
