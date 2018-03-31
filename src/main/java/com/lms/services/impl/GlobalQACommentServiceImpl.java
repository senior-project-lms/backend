package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.GlobalQAComment;
import com.lms.entities.GlobalQA;
import com.lms.entities.User;
import com.lms.pojos.GlobalQACommentPojo;
import com.lms.repositories.GlobalQACommentRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.GlobalQACommentService;
import com.lms.services.interfaces.GlobalQAService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalQACommentServiceImpl implements GlobalQACommentService {


    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private GlobalQACommentRepository globalQACommentRepository;

    @Autowired
    private GlobalQAService globalQAService;


    /**
     * Converts GlobalQAComment entity to GlobalAnswer pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @author emsal aynaci
     * @updatedBy umit.kas
     * @param entity
     * @return GlobalQACommentPojo
     */

    @Override
    public GlobalQACommentPojo entityToPojo(GlobalQAComment entity) {

       GlobalQACommentPojo pojo = new GlobalQACommentPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setContent(entity.getContent());
        if (!entity.isAnonymous()){
            pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        }
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setUpdatedAt(entity.getUpdatedAt());
        return pojo;

    }


    /**
     * Converts GlobalQACommentPojo to GlobalQAComment according to values, if the value is null passes it,

     * @author emsal aynaci
     * @param pojo
     * @return GlobalQAComment
     */

    @Override
    public GlobalQAComment pojoToEntity(GlobalQACommentPojo pojo)
    {
       GlobalQAComment entity = new GlobalQAComment();
       entity.setContent(entity.getContent());
       return entity;
    }

    /**
     *
     * saves comment
     *
     * @author umit.kas
     * @param qaPublicKey
     * @param pojo

     * @return boolean
     */
    @Override
    public boolean save(String qaPublicKey, GlobalQACommentPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = userDetailService.getAuthenticatedUser();

        GlobalQA globalquestion = globalQAService.findByPublicKey(qaPublicKey, true);

        GlobalQAComment entity = pojoToEntity(pojo);
        entity.setCreatedBy(authenticatedUser);
        entity.setQa(globalquestion);

        entity = globalQACommentRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException("Comment is not saved");
        }

        return true;
    }
}

