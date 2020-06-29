package com.example.demo.repos;

import com.example.demo.models.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentsRepo extends MongoRepository<Document, String> {
}