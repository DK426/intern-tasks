package com.example.demo.repos;

import com.example.demo.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestsRepo extends MongoRepository<Request, String> {
}