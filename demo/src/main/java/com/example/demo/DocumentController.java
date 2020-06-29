package com.example.demo;

import com.example.demo.models.Document;
import com.example.demo.models.Request;
import com.example.demo.repos.DocumentsRepo;
import com.example.demo.repos.RequestsRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentsRepo repository;
    @Autowired
    private RequestsRepo requestsRepo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Document> getDocumentById(@PathVariable("id") String id) {
        if (repository.findById(id).isPresent())
            return ResponseEntity.ok().body(repository.findById(id).get());
        return new ResponseEntity("No request with id " + id, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> endRequestById(@PathVariable("id") String id) {

        if(requestsRepo.findById(id).isPresent()){
            Request request = requestsRepo.findById(id).get();
            request.endRequest();
            requestsRepo.save(request);
            if(repository.findById(id).isPresent()){
                Document document = repository.findById(id).get();
                document.status = true;
                repository.save(document);
                return new ResponseEntity<>("Document's with id " + id + "status = " + document.status +
                        "\nRequest proceed successfully", HttpStatus.OK);
            } else return new ResponseEntity<>("No document with id " + id + "to change status", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>("No request with id " + id, HttpStatus.NOT_FOUND);


    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Document createDocument(@RequestBody Document document) {
        document.setId(ObjectId.get().toHexString());
        repository.save(document);
        Request request = new Request(document.getId());
        requestsRepo.save(request);
        return document;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteDocument(@PathVariable String id) {
        short operationStatus = 0;
        if (repository.findById(id).isPresent())
            repository.delete(repository.findById(id).get());
        else operationStatus += 1;

        if (requestsRepo.findById(id).isPresent())
            requestsRepo.delete(requestsRepo.findById(id).get());
        else   operationStatus += 10;

        if (operationStatus != 0){
            if (operationStatus == 1)
                return new ResponseEntity<String>("There is no document with id " + id, HttpStatus.NOT_FOUND);
            if (operationStatus == 10)
                return new ResponseEntity<String>("There is no request with id " + id, HttpStatus.NOT_FOUND);
            if (operationStatus == 11)
                return new ResponseEntity<String>("There are no document and request with id " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Document & Request deleted successfully", HttpStatus.OK);
    }
}

