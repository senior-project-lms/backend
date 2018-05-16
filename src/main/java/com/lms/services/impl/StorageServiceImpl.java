package com.lms.services.impl;

import com.lms.customExceptions.StorageException;
import com.lms.customExceptions.StorageFileNotFoundException;
import com.lms.properties.StorageProperties;
import com.lms.services.interfaces.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StorageProperties properties;


    /**
     * get all paths from properties, then generarates the paths, when application is run
     *
     * @param
     * @return
     * @author umit.kas
     */
    @Override
    public void init() {

        try {
            for(String path : properties.getAllPaths()){
                Files.createDirectories(Paths.get(path));
            }

        } catch (IOException e){
            throw new StorageException("Could not initialize storage", e);
        }
    }


    /**
     * Save file to the path
     *
     * @author umit.kas
     * @param path
     * @param  filename
     * @param file
     * @return
     *
     */
    @Override
    public void store(String path, String filename, MultipartFile file) {

        final Path p = Paths.get(path);


        if (Files.notExists(p)){
            try {
                Files.createFile(Files.createDirectories(p)).toFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }

            Files.copy(file.getInputStream(), p.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }


    }



    /**
     * loads the file, according to path and filename
     *
     * @author umit.kas
     * @param path
     * @param filename
     * @return
     *
     */
    @Override
    public Path load(String path, String filename) {
        Path p = Paths.get(path);
        return p.resolve(filename);
    }


    /**
     * loads the file as resource, according to path and filename
     *
     * @author umit.kas
     * @param path
     * @param filename
     * @return Resource
     *
     */
    @Override
    public Resource loadAsResource(String path, String filename) {
        try {
            Path file = this.load(path, filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {

    }

    /**
     * delete file by path and filename, if it is exist
     *
     * @author umit.kas
     * @param path
     * @param filename
     * @return
     *
     */

    @Override
    public void delete(String path, String filename) {
        try {
            Path file = this.load(path, filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                Files.deleteIfExists(file);
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }
        catch (IOException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }

    }
}
