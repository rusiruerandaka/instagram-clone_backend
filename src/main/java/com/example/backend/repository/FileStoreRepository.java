package com.example.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend.model.FileStore;

public interface FileStoreRepository extends MongoRepository<FileStore, String> {
    FileStore findByName(String name);
}
