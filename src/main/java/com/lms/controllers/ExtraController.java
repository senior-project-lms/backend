package com.lms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(value = {"/api"})
public class ExtraController {

    @GetMapping(value = {"/current-time"})
    public HashMap<String, Date> getCurrentTime() {
        Calendar calendar = Calendar.getInstance();

        HashMap<String, Date> map = new HashMap<>();

        map.put("currentTime", calendar.getTime());
        return map;
    }
}
