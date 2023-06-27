package com.example.pdf.controller;

import com.example.pdf.entity.Comment;
import com.example.pdf.entity.PdfEntity;
import com.example.pdf.entity.Post;
import com.example.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PdfController {

    @Autowired
    PdfService pdfService;

    @GetMapping("/get")
    public String getPdf() {
        return "Hello World";
    }

    @PostMapping("/upload")
    public ResponseEntity uploadPdf(@RequestParam("uploadpdf") MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().contains(".pdf")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return pdfService.uploadPdf(file);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<PdfEntity>> getAll() {
        return pdfService.getAllPdf();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PdfEntity> getPdfById(@PathVariable int id) {
        return pdfService.getPdfById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable int id) {
        return pdfService.deleteById(id);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<List<PdfEntity>> deleteAll() {
        return pdfService.deleteAll();
    }

    @PostMapping("/create/post")
    public ResponseEntity createPost(@RequestBody Post post) {
        return pdfService.createPost(post);
    }

    @PostMapping("/create/comment")
    public ResponseEntity createComment(@RequestBody Comment comment) {
        return pdfService.createComment(comment);
    }
}
