package com.lms.controllers;

import com.lms.components.ExceptionConverter;
import com.lms.customExceptions.*;
import com.lms.pojos.SystemAnnouncementPojo;
import com.lms.services.interfaces.SystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class SystemAnnouncementController {

    private final String systemAnnouncementServeFileURLPath = "/api/system-announcement/storage/file";

    @Autowired
    private SystemAnnouncementService systemAnnouncementService;

    private ExceptionConverter exceptionConverter;

    /**
     * Checks the parameters that will be saved, is null or not, if there is any null returns error else
     * Save system announcement
     *
     * @param pojo
     * @return
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.enums.Privilege).SAVE_SYSTEM_ANNOUNCEMENT)")
    @PostMapping(value = {"/admin/system-announcement"})
    public boolean save(@RequestBody SystemAnnouncementPojo pojo) throws EmptyFieldException, ExecutionFailException, DataNotFoundException, ExistRecordException {

            if (pojo == null){
                throw new EmptyFieldException("System Announcement object cannot be empty");
            }
            else if (pojo.getTitle() == null || pojo.getTitle().isEmpty()){
                throw new EmptyFieldException("Title field cannot be empty");
            }
            else if (pojo.getContent() == null || pojo.getContent().isEmpty()){
                throw new EmptyFieldException("Content field cannot be empty");
            }
            else{
                try {
                    return systemAnnouncementService.save(pojo);
                }
                catch (ServiceException e){
                    exceptionConverter.convert(e);
                }


            }
            throw new ExecutionFailException("No system announcement is found");

    }

    /**
     * Checks the parameters that is null or not, if there is any null returns error else
     * delete system announcement
     *
     * @param publicKey
     * @return
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.enums.Privilege).DELETE_SYSTEM_ANNOUNCEMENT)")
    @DeleteMapping(value = {"/admin/system-announcement/{publicKey}"})
    public boolean delete(@PathVariable String publicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException, ExistRecordException {

        if (publicKey == null || publicKey.isEmpty()){
            throw new EmptyFieldException("publicKey field cannot be empty");
        }
        try {
            return systemAnnouncementService.delete(publicKey);
        }
        catch (ServiceException ex){
            exceptionConverter.convert(ex);
        }

        throw new ExecutionFailException("No such system announcement is deleted");

    }

    /**
     * Gets a path parameter, which is the page number in database,
     * return 5 of System Announcement, for each page, for each request
     *
     * @param page
     * @return List<SystemAnnouncementPojo>
     * @author umit.kas
     */

    @GetMapping({"/system-announcements/{page}"})
    public List<SystemAnnouncementPojo> getAnnouncements(@PathVariable int page) throws EmptyFieldException, ExecutionFailException, DataNotFoundException, ExistRecordException {

        if (page < 0){
            throw new EmptyFieldException("Page number cannot be negative");
        }
        try {
            return systemAnnouncementService.getAllByPage(page);
        }
        catch (ServiceException ex){
            exceptionConverter.convert(ex);
        }
        throw new ExecutionFailException("");


    }

}
