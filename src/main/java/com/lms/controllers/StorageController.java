package com.lms.controllers;


import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.EmptyFieldException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.pojos.SystemResourcePojo;
import com.lms.pojos.course.CourseResourcePojo;
import com.lms.properties.StorageProperties;
import com.lms.services.interfaces.CourseResourceService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/api"})
public class StorageController {

    private final String systemAnnouncementServeFileURLPath = "/api/system-announcement/storage/file";

    private String getCourseServeFileURLPath(String coursePublicKey){
        return String.format("/api/course/%s/storage/file", coursePublicKey);
    }


    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageProperties properties;

    @Autowired
    private SystemResourceService systemResourceService;

    @Autowired
    private CourseResourceService courseResourceService;




    /**
     *
     * Upload file that is added the of System Announcement,
     *
     * @param file
     * @return SystemResourcePojo
     * @author umit.kas
     */
    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).UPLOAD_SYSTEM_ANNOUNCEMENT_FILE.CODE)")
    @PostMapping(value = {"/system-announcement/storage/file"})
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
    @PreAuthorize("hasRole(T(com.lms.enums.EPrivilege).DELETE_SYSTEM_ANNOUNCEMENT_FILE.CODE)")
    @DeleteMapping(value = {"/system-announcement/storage/file/{publicKey}"})
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



    @GetMapping({"/course/{coursePublicKey}/resources"})
    public List<CourseResourcePojo> getCourseResources(@PathVariable String coursePublicKey) throws EmptyFieldException,DataNotFoundException{

        return courseResourceService.getCourseResources(coursePublicKey);
    }

    @PutMapping(value = {"course/{coursePublicKey}/resource/{resourcePublicKey}/public"})
    public boolean publiclyShared(@PathVariable String coursePublicKey, @PathVariable String resourcePublicKey) throws ExecutionFailException, DataNotFoundException {
        return courseResourceService.publiclyShared(resourcePublicKey, true);
    }

    @PutMapping(value = {"course/{coursePublicKey}/resource/{resourcePublicKey}/private"})
    public boolean publiclyUnShared(@PathVariable String coursePublicKey,@PathVariable String resourcePublicKey) throws ExecutionFailException, DataNotFoundException {
        return courseResourceService.publiclyShared(resourcePublicKey, false);
    }


    @DeleteMapping(value = {"/course/{coursePublicKey}/resource/{resourcePublicKey}"})
    public boolean deleteCourseResource(@PathVariable String coursePublicKey, @PathVariable String resourcePublicKey) throws EmptyFieldException, ExecutionFailException, DataNotFoundException {
        if (coursePublicKey == null || coursePublicKey.isEmpty()) {
            throw new EmptyFieldException("field cannot be empty");
        }
        return courseResourceService.delete(resourcePublicKey);

    }
    /**
     *
     * Upload file that is added the of Course,
     *
     * @param file
     * @return CourseResourcePojo
     * @author emsal aynaci
     */
    @PostMapping(value = {"/course/{coursePublicKey}/storage/file"})
    public CourseResourcePojo courseUploadFile(@PathVariable String coursePublicKey, @RequestParam MultipartFile file){

        return courseUpload(coursePublicKey, properties.getCourseFilePath(coursePublicKey), file);
    }

    /**
     * Serve file that is added to the Course,
     * Finds the file by filename, that comes with request
     *
     * @param filename
     * @return CourseResourcePojo
     * @author emsal aynaci
     */
    @GetMapping(value = {"/course/{coursePublicKey}/storage/file/{filename:.+}"})
    @ResponseBody
    public ResponseEntity<Resource> courseServeFile(@PathVariable String coursePublicKey, @PathVariable String filename){
        Resource file = storageService.loadAsResource(properties.getCourseFilePath(coursePublicKey), filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @DeleteMapping(value = {"/courses/{coursePublicKey}/storage/file/{resourcePublicKey}"})
    public boolean courseDeleteFile(@PathVariable String coursePublicKey,@PathVariable String resourcePublicKey) throws ExecutionFailException, DataNotFoundException{

        CourseResourcePojo pojo = courseResourceService.getByPublicKey(resourcePublicKey);
        if( courseResourceService.delete(pojo.getPublicKey())){
            try {
                storageService.delete(properties.getCourseFilePath(coursePublicKey), pojo.getName());
            }
            catch (Exception e){
                return false;

            }
        }
        return true;
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


    /**
     *
     *  functions for uploading, save file to given path,
     * before saving, get the extention type, generates unique name, than saves to file system
     * after save, insert to record to database
     *
     * @param path
     * @param file
     * @return CourseResourcePojo
     * @author emsal aynaci
     */
    private CourseResourcePojo courseUpload(String coursePublicKey, String path, MultipartFile file){
        try {
            if (file != null){

                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                String filename = String.format("%s.%s", UUID.randomUUID().toString(), extension);

                String URL = String.format("%s/%s", getCourseServeFileURLPath(coursePublicKey), filename);

                storageService.store(path, filename, file);

                Path f = storageService.load(path, filename);

                if (f != null){

                    CourseResourcePojo pojo = new CourseResourcePojo();
                    pojo.setPath(f.toString());
                    pojo.setName(f.getFileName().toString());
                    pojo.setOriginalFileName(file.getOriginalFilename());
                    pojo.setUrl(URL);
                    pojo.setType(extension);
                    courseResourceService.save(coursePublicKey,pojo);
                    pojo = courseResourceService.getByName(pojo.getName());


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
