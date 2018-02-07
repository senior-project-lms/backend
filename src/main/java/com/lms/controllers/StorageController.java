package com.lms.controllers;


import com.lms.pojos.SystemResourcePojo;
import com.lms.properties.StorageProperties;
import com.lms.services.interfaces.StorageService;
import com.lms.services.interfaces.SystemResourceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    /**
     * Not Used for now, because of the bug in frontend
     * <p>
     * Uploads image that is located in System Announcement text,
     *
     * @param file
     * @return SystemResourcePojo
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.enums.Privilege).UPLOAD_SYSTEM_ANNOUNCEMENT_FILE)")
    @PostMapping(value = {"/admin/system-announcement/storage/image"})
    public SystemResourcePojo systemAnnouncementUploadImage(@RequestParam MultipartFile file){

        return  upload(properties.getSystemAnnouncementImagePath(), file);

    }


    /**
     * Not Used for now, because of the bug in frontend
     * <p>
     * Serve image that is located in System Announcement text,
     *
     * @param
     * @return ResponseEntity<Resource>
     * @author umit.kas
     */
    @GetMapping(value = {"/system-announcement/storage/image/{filename:.+}"})
    @ResponseBody
    public ResponseEntity<Resource> systemAnnouncementServeImage(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(properties.getSystemAnnouncementImagePath(), filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    /**
     * Not Used for now, because of the bug in frontend
     * <p>
     * Delete image that is located in System Announcement text,
     *
     * @param publicKey
     * @return
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.enums.Privilege).DELETE_SYSTEM_ANNOUNCEMENT_FILE)")
    @DeleteMapping(value = {"/admin/system-announcement/storage/image/{publicKey}"})
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

    /**
     *
     * Upload file that is added the of System Announcement,
     *
     * @param file
     * @return SystemResourcePojo
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.enums.Privilege).UPLOAD_SYSTEM_ANNOUNCEMENT_FILE)")
    @PostMapping(value = {"/admin/system-announcement/storage/file"})
    public SystemResourcePojo systemAnnouncementUploadFile(@RequestParam MultipartFile file){

        return  upload(properties.getSystemAnnouncementFilePath(), file);
    }


    /**
     * Serve file that is added to the System Announcement,
     * Finds the file by filename, that comes with request
     *
     * @param filename
     * @return SystemResourcePojo
     * @author umit.kas
     */
    @GetMapping(value = {"/system-announcement/storage/file/{filename:.+}"})
    @ResponseBody
    public ResponseEntity<Resource> systemAnnouncementServeFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(properties.getSystemAnnouncementFilePath(), filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    /**
     *
     * Delete file that is added to the System Announcement,
     * Delete the file by publicKey, that comes with request path
     *
     * @param publicKey
     * @return SystemResourcePojo
     * @author umit.kas
     */
    @PreAuthorize("@methodSecurity.hasAccessPrivilege(T(com.lms.enums.Privilege).DELETE_SYSTEM_ANNOUNCEMENT_FILE)")
    @DeleteMapping(value = {"/admin/system-announcement/storage/file/{publicKey}"})
    public boolean systemAnnouncementDeleteFile(@PathVariable String publicKey){
        try {

            SystemResourcePojo pojo = systemResourceService.getByPublicKey(publicKey);
            if (pojo != null && systemResourceService.delete(pojo.getPublicKey())){
                storageService.delete(properties.getSystemAnnouncementFilePath(), pojo.getName());
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }


    /**
     *
     * common funtion for uploading, save file to given path,
     * before saving, get the extention type, generates unique name, than saves to file system
     * after savege, insert to record to database
     *
     * @param path
     * @param file
     * @return SystemResourcePojo
     * @author umit.kas
     */
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
                    pojo.setType(extension);
                    systemResourceService.save(pojo);
                    pojo = systemResourceService.getByName(pojo.getName());


                    return pojo;
                }
            }

            return null;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }





}
