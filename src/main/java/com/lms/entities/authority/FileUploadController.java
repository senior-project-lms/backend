package com.lms.entities.authority;


import com.lms.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api"})
public class FileUploadController {

    @Autowired
    private StorageService storageService;
}
