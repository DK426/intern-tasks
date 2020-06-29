package com.example.demo.models;


import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;

public class Document {
    @Id
    private String _id;

    public String filename;
    public boolean status;
    public JSONObject file;


    public Document() {
        status = false;
    }

    public Document(String name, JSONObject file) {
        filename = name;
        this.file = file;
        status = false;
    }

    public Document(ObjectId _id, JSONObject file) {
        this.file = file;
        status = false;
    }

    public String getId() { return _id; }
    public void setId(String _id) { this._id = _id; }
}