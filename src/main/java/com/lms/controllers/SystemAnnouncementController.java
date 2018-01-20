package com.lms.controllers;

import com.lms.pojos.SystemAnnouncementPojo;
import com.lms.services.SystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SystemAnnouncementController {

    @Autowired
    private SystemAnnouncementService systemAnnouncementService;

    @RequestMapping(value = {"/api/admin/system-announcement"}, method = RequestMethod.POST)
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


    @RequestMapping(value = {"/api/admin/system-announcement/{publicKey}"}, method = RequestMethod.DELETE)
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


    @RequestMapping(value = {"/api/system-announcements/{page}"}, method = RequestMethod.GET)
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
