package com.lms.services.storage;

import com.lms.customExceptions.StorageException;
import com.lms.customExceptions.StorageFileNotFoundException;
import com.lms.services.interfaces.StorageService;
import com.lms.properties.custom.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());




    private  Path rootLocation;


    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation  = Paths.get(properties.getRootPath());
    }

    @Override
    public void init() {

        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e){
            throw new StorageException("Could not initialize storage", e);
        }
    }


    @Override
    public void store(MultipartFile file) {


            try {

                if (file.isEmpty()) {
                    throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
                }
                Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            }
            catch (IOException e) {
                throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
            }


    }



    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }    }

    @Override
    public void deleteAll() {

    }






}
