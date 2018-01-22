package com.lms.controllers;


import com.lms.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = {"/api"})
public class StorageController {


    @Autowired
    private StorageService storageService;

    @RequestMapping(value = {"/admin/upload"}, method = RequestMethod.POST)
    public void adminUploadFile(@RequestParam MultipartFile[] files){
        if (files != null){
            storageService.store(files);
        }

    }

}
