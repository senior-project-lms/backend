package com.lms.controllers;


import com.lms.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping(value = {"/api"})
public class StorageController {


    @Autowired
    private StorageService storageService;

    @RequestMapping(value = {"/admin/upload"}, method = RequestMethod.POST)
    public Path adminUploadFile(@RequestParam MultipartFile file){
        try {
            if (file != null){
                storageService.store(file);
                return storageService.load(file.getOriginalFilename());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}
