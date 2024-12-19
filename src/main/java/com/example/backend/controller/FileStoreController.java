package com.example.backend.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.service.FileStoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin
@RestController
public class FileStoreController {

    @Autowired
    private FileStoreService fileStoreService;

    @PostMapping("/uploadPost")
    public ResponseEntity<?> uploadPostImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = fileStoreService.uploadPostImage(file);
        
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/downloadPost/{fileName}")
	public ResponseEntity<?> downloadPostImage(@PathVariable String fileName) throws IOException {
		byte[] imageData=fileStoreService.downloadPostImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}   

    @PostMapping("/uploadStory")
    public ResponseEntity<?> uploadStoryImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = fileStoreService.uploadStoryImage(file);
        
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/downloadStory/{fileName}")
	public ResponseEntity<?> downloadStoryImage(@PathVariable String fileName) throws IOException {
		byte[] imageData=fileStoreService.downloadStoryImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}  

    @PostMapping("/uploadUser")
    public ResponseEntity<?> uploadUserImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = fileStoreService.uploadUserImage(file);
        
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/downloadUser/{fileName}")
	public ResponseEntity<?> downloadUserImage(@PathVariable String fileName) throws IOException {
		byte[] imageData=fileStoreService.downloadUserImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}  
    


}
