package com.lms.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    void store(String path, String filename, MultipartFile file);

    Path load(String path, String filename);

    Resource loadAsResource(String path, String filename);

    void deleteAll();

    void delete(String path, String filename);

}
