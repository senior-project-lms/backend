package com.lms.controllers;

import com.lms.pojos.global.SystemAnnouncementPojo;
import com.lms.services.impl.global.SystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class SystemAnnouncementController {

    @Autowired
    private SystemAnnouncementService systemAnnouncementService;



    @PreAuthorize("@methodSecurity.hasAdminPrivilege(T(com.lms.properties.Privileges).SAVE_SYSTEM_ANNOUNCEMENT)")
    @RequestMapping(value = {"/admin/system-announcement"}, method = RequestMethod.POST)
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

    @PreAuthorize("@methodSecurity.hasAdminPrivilege(T(com.lms.properties.Privileges).DELETE_SYSTEM_ANNOUNCEMENT)")
    @RequestMapping(value = {"/admin/system-announcement/{publicKey}"}, method = RequestMethod.DELETE)
    public boolean delete(@PathVariable String publicKey){
        if (publicKey != null){
            try {
                return systemAnnouncementService.delete(publicKey);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    @RequestMapping(value = {"/system-announcements/{page}"}, method = RequestMethod.GET)
    public List<SystemAnnouncementPojo> getAnnouncements(@PathVariable int page){
        try {
            return systemAnnouncementService.getAnnouncements(page);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
