package com.example.pdf.service;

import com.example.pdf.entity.Comment;
import com.example.pdf.entity.PdfEntity;
import com.example.pdf.entity.Post;
import com.example.pdf.repository.PdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PdfService {
    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    RestTemplate restTemplate;
    public ResponseEntity uploadPdf (MultipartFile file) throws IOException {
        PdfEntity pdfEntity = new PdfEntity();
        pdfEntity.setName("PDF1");
        pdfEntity.setDocumentData(file.getBytes());
        pdfRepository.save(pdfEntity);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<List<PdfEntity>> getAllPdf() {
        List<PdfEntity> pdfList = pdfRepository.findAll();
        setPostAndComment(pdfList);
        return new ResponseEntity<>(pdfList, HttpStatus.OK);
    }

    public ResponseEntity<PdfEntity> getPdfById(int id) {
        Optional<PdfEntity> pdf = pdfRepository.findById(Long.valueOf(id));
        pdf.get().setComment(Arrays.asList(restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments?id="+id, Comment[].class)));
        pdf.get().setPost(Arrays.asList(restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts?id="+id, Post[].class)));
        return new ResponseEntity<>(pdf.get(), HttpStatus.OK);
    }

    public ResponseEntity deleteById(int id) {
        pdfRepository.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<PdfEntity>> deleteAll() {
        pdfRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity createPost(Post post) {
        ResponseEntity<Post> p = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/posts", post, Post.class);
        if (p.getStatusCode() != HttpStatus.CREATED) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    public ResponseEntity createComment(Comment comment) {
        ResponseEntity<Comment> p = restTemplate.postForEntity("https://jsonplaceholder.typicode.com/comments", comment, Comment.class);
        if (p.getStatusCode() != HttpStatus.CREATED) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    private List<PdfEntity> setPostAndComment(List<PdfEntity> entityList) {
        List<Post> postList = Arrays.asList(restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class));
        List<Comment> commentList = Arrays.asList(restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments", Comment[].class));
        return entityList.stream().map(x-> {
            x.setComment(commentList.stream().filter(y-> Long.valueOf(y.getId()) == x.getId()).collect(Collectors.toList()));
            x.setPost(postList.stream().filter(y-> Long.valueOf(y.getId()) == x.getId()).collect(Collectors.toList()));
            return x;
        }).collect(Collectors.toList());
    }
}
