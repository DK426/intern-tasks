package com.example.demo.models;

import org.bson.types.ObjectId;
import java.time.LocalDateTime;

public class Request {
    public String _id;

    public LocalDateTime created;
    public LocalDateTime processed;

    public Request(String docId){
        this._id = docId;
        created = LocalDateTime.now();
    }

    public void endRequest() {
        processed = LocalDateTime.now();
    }
}
