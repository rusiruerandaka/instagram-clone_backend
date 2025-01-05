package com.example.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

    @Service
    public class FileStoreService {

        private final String FOLDER_PATH_POSTS = System.getProperty("user.dir") + "/uploads/posts/";
        private final String FOLDER_PATH_STORY = System.getProperty("user.dir") + "/uploads/stories/";
        private final String FOLDER_PATH_USERS = System.getProperty("user.dir") + "/uploads/users/";
        //private final String FOLDER_PATH = "C:\\Users\\thara\\Downloads\\Backend\\";

        public String uploadPostImage(MultipartFile file) throws IOException {

            File directory = new File(FOLDER_PATH_POSTS);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFileName = generateUniqueFileName(originalFilename, fileExtension, "post");

            String filePath=FOLDER_PATH_POSTS + uniqueFileName;

            file.transferTo(new File(filePath));

            {
                return uniqueFileName;
            }
        }
        

        public byte[] downloadPostImage(String fileName) throws IOException {

            //Optional<FileStore> fileData = fileStoreRepository.findByName(fileName);
            String filePath=FOLDER_PATH_POSTS + fileName;
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }

        public String uploadStoryImage(MultipartFile file) throws IOException {

            File directory = new File(FOLDER_PATH_STORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFileName = generateUniqueFileName(originalFilename, fileExtension, "story");

            String filePath=FOLDER_PATH_STORY + uniqueFileName;

            file.transferTo(new File(filePath));

            {
                return uniqueFileName;
            }
        }
        

        public byte[] downloadStoryImage(String fileName) throws IOException {

            //Optional<FileStore> fileData = fileStoreRepository.findByName(fileName);
            String filePath=FOLDER_PATH_STORY + fileName;
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }

        public String uploadUserImage(MultipartFile file) throws IOException {

            File directory = new File(FOLDER_PATH_USERS);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFileName = generateUniqueFileName(originalFilename, fileExtension, "user");

            String filePath=FOLDER_PATH_USERS + uniqueFileName;

            file.transferTo(new File(filePath));

            {
                return uniqueFileName;
            }
        }
        

        public byte[] downloadUserImage(String fileName) throws IOException {

            //Optional<FileStore> fileData = fileStoreRepository.findByName(fileName);
            String filePath=FOLDER_PATH_USERS + fileName;
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }


        // Custom methods
        // Generate unique file name
        private String generateUniqueFileName(String originalFileName, String fileExtension, String type) {
            String datePart = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // Date: yyyyMMddHHmmss
            String randomPart = UUID.randomUUID().toString().substring(0, 8); // Random code: first 8 characters of UUID
            return originalFileName + "_" + datePart + "_" + randomPart + "_" + type + fileExtension;
        }

        // Extract file extension
        private String getFileExtension(String fileName) {
            if (fileName != null && fileName.contains(".")) {
                return fileName.substring(fileName.lastIndexOf("."));
            }
            return ""; // Default: no extension
        }
    }
