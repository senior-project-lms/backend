package com.lms.services.impl.storage;

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
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StorageProperties properties;


    @Override
    public void init() {

        try {
            for(String path : properties.getAllPaths()){
                Files.createDirectories(Paths.get(path));
            }

        }
        catch (IOException e){
            throw new StorageException("Could not initialize storage", e);
        }
    }



    @Override
    public void store(String path, String filename, MultipartFile file) {

        Path p = Paths.get(path);

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



    @Override
    public Path load(String path, String filename) {
        Path p = Paths.get(path);
        return p.resolve(filename);
    }

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
