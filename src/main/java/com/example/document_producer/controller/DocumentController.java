package com.example.document_producer.controller;

import com.example.document_producer.entity.SearchRequest;
import com.example.document_producer.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<String> searchDocuments(@RequestBody SearchRequest request) {
            documentService.searchDocumentFromKey(request.getKeyword());
            return ResponseEntity.ok("Search completed successfully.");
        }

}
