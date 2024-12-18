package com.example.backend.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.model.FileStore;
import com.example.backend.repository.FileStoreRepository;

    @Service
    public class FileStoreService {

        private final String FOLDER_PATH = System.getProperty("user.dir") + "/uploads/images/";;
        
        @Autowired
        private FileStoreRepository fileStoreRepository;

        public String uploadImage(MultipartFile file) throws IOException {
            File directory = new File(FOLDER_PATH);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directories if they don't exist
            }
        
            String filePath = FOLDER_PATH + file.getOriginalFilename();
        
            FileStore fileStore = fileStoreRepository.save(FileStore.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build()
            );
        
            file.transferTo(new File(filePath));
        
            return "File uploaded successfully: " + filePath;
        }
        

        public byte[] downloadImage(String fileName) throws IOException {
            FileStore fileStore = fileStoreRepository.findByName(fileName);
            if (fileStore == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }

            String filePath = fileStore.getFilePath();
            return Files.readAllBytes(new File(filePath).toPath());
        }

    }
