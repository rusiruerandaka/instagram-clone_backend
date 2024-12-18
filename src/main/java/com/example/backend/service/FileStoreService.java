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

        private final String FOLDER_PATH = System.getProperty("user.dir") + "/uploads/images/";
        //private final String FOLDER_PATH = "C:\\Users\\thara\\Downloads\\Backend\\";

        public String uploadImage(MultipartFile file) throws IOException {

            File directory = new File(FOLDER_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFileName = generateUniqueFileName(originalFilename, fileExtension);

            String filePath=FOLDER_PATH+uniqueFileName;

            file.transferTo(new File(filePath));

            {
                return uniqueFileName;
            }
        }
        

        public byte[] downloadImageFromFileSystem(String fileName) throws IOException {

            System.out.println("\n\n\n\n\n\n"+System.getProperty("user.dir")+"\n\n\n\n\n\n");

            //Optional<FileStore> fileData = fileStoreRepository.findByName(fileName);
            String filePath=FOLDER_PATH+fileName;
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }


        // Custom methods
        // Generate unique file name
        private String generateUniqueFileName(String originalFileName, String fileExtension) {
            String datePart = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // Date: yyyyMMddHHmmss
            String randomPart = UUID.randomUUID().toString().substring(0, 8); // Random code: first 8 characters of UUID
            return originalFileName + "_" + datePart + "_" + randomPart + fileExtension;
        }

        // Extract file extension
        private String getFileExtension(String fileName) {
            if (fileName != null && fileName.contains(".")) {
                return fileName.substring(fileName.lastIndexOf("."));
            }
            return ""; // Default: no extension
        }
    }
