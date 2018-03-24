package com.lms.controllers;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.pojos.SystemEventPojo;
import com.lms.services.interfaces.SystemEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class SystemEventController {

    @Autowired
    private SystemEventService systemEventService;


    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).SAVE_GLOBAL_CALENDAR.CODE)")
    @PostMapping(value = {"/system-event"})
    public boolean saveEvent(@RequestBody SystemEventPojo pojo) {
        return systemEventService.save(pojo);
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).DELETE_GLOBAL_CALENDAR.CODE)")
    @DeleteMapping(value = {"/system-event/{publicKey}"})
    public boolean deleteEvent(@PathVariable String publicKey) throws DataNotFoundException {
        return systemEventService.delete(publicKey);
    }

    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).READ_GLOBAL_CALENDAR.CODE)")
    @GetMapping(value = {"/system-events"})
    public List<SystemEventPojo> getEvents() {
        return systemEventService.getAllEvents();
    }


}
