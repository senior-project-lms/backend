package com.lms.controllers;


import com.lms.entities.course.CourseResource;
import com.lms.pojos.global.SystemResourcePojo;
import com.lms.properties.custom.StorageProperties;
import com.lms.services.interfaces.StorageService;
import com.lms.services.interfaces.SystemResourceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/api"})
public class StorageController {

    private final String systemAnnouncementServeFileURLPath = "/api/system-announcement/storage/file";

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageProperties properties;

    @Autowired
    private SystemResourceService systemResourceService;




    @RequestMapping(value = {"/admin/system-announcement/storage/image"}, method = RequestMethod.POST)
    public SystemResourcePojo systemAnnouncementUploadImage(@RequestParam MultipartFile file){

        return  upload(properties.getSystemAnnouncementImagePath(), file);

    }


    @RequestMapping(value = {"/system-announcement/storage/image/{filename:.+}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> systemAnnouncementServeImage(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(properties.getSystemAnnouncementImagePath(), filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @RequestMapping(value = {"/admin/system-announcement/storage/file"}, method = RequestMethod.POST)
    public SystemResourcePojo systemAnnouncementUploadFile(@RequestParam MultipartFile file){

        return  upload(properties.getSystemAnnouncementFilePath(), file);
    }

    @RequestMapping(value = {"/system-announcement/storage/file/{filename:.+}"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> systemAnnouncementServeFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(properties.getSystemAnnouncementFilePath(), filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @RequestMapping(value = {"/admin/system-announcement/storage/image/{publicKey}"}, method = RequestMethod.DELETE)
    public void systemAnnouncementDeleteImage(@PathVariable String publicKey){
        try {

            SystemResourcePojo pojo = systemResourceService.getByPublicKey(publicKey);
            if (pojo != null){
                storageService.delete(properties.getSystemAnnouncementImagePath(), pojo.getName());
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @RequestMapping(value = {"/admin/system-announcement/storage/file/{publicKey}"}, method = RequestMethod.DELETE)
    public boolean systemAnnouncementDeleteFile(@PathVariable String publicKey){
        try {

            SystemResourcePojo pojo = systemResourceService.getByPublicKey(publicKey);
            if (pojo != null && systemResourceService.delete(pojo.getPublicKey())){
                storageService.delete(properties.getSystemAnnouncementFilePath(), pojo.getName());
                return true;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }



    private SystemResourcePojo upload(String path, MultipartFile file){
        try {
            if (file != null){

                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                String filename = String.format("%s.%s", UUID.randomUUID().toString(), extension);

                String URL = String.format("%s/%s", systemAnnouncementServeFileURLPath, filename);

                storageService.store(path, filename, file);

                Path f = storageService.load(path, filename);

                if (f != null){
                    SystemResourcePojo pojo = new SystemResourcePojo();
                    pojo.setPath(f.toString());
                    pojo.setName(f.getFileName().toString());
                    pojo.setOriginalFileName(file.getOriginalFilename());
                    pojo.setUrl(URL);
                    systemResourceService.save(pojo);
                    pojo = systemResourceService.getByName(pojo.getName());



                    return pojo;
                }
            }

            return null;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }





}
