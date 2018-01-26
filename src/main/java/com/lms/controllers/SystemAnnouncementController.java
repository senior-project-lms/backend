package com.lms.controllers;

import com.lms.pojos.SystemAnnouncementPojo;
import com.lms.services.interfaces.SystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class SystemAnnouncementController {

    private final String systemAnnouncementServeFileURLPath = "/api/system-announcement/storage/file";

    @Autowired
    private SystemAnnouncementService systemAnnouncementService;


    /**
     * Checks the parameters that will be saved, is null or not, if there is any null returns error else
     * Save system announcement
     *
     * @param pojo
     * @return
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.properties.Privileges).SAVE_SYSTEM_ANNOUNCEMENT)")
    @PostMapping(value = {"/admin/system-announcement"})
    public boolean save(@RequestBody SystemAnnouncementPojo pojo){
        try{
            if (pojo.getTitle() != null && !pojo.getTitle().isEmpty() && pojo.getContent() != null && !pojo.getContent().isEmpty()){
                return systemAnnouncementService.save(pojo);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Checks the parameters that is null or not, if there is any null returns error else
     * delete system announcement
     *
     * @param publicKey
     * @return
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.properties.Privileges).DELETE_SYSTEM_ANNOUNCEMENT)")
    @DeleteMapping(value = {"/admin/system-announcement/{publicKey}"})
    public boolean delete(@PathVariable String publicKey){
        if (publicKey != null){
            try {
                return systemAnnouncementService.delete(publicKey);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
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
    public List<SystemAnnouncementPojo> getAnnouncements(@PathVariable int page){
        try {
            return systemAnnouncementService.getAnnouncements(page);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
