package com.aseds.blogsmicroservice.blogs_microservice.controller;

import com.aseds.blogsmicroservice.blogs_microservice.models.dto.BlogDto;
import com.aseds.blogsmicroservice.blogs_microservice.services.BlogService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    private final BlogService blogService;
    @Autowired
    public BlogController(BlogService service){
        this.blogService=service;
    }

    @GetMapping("/")
    public ResponseEntity<List<BlogDto>> getBlogs(){
        return ResponseEntity.ok(this.blogService.getAllBlogs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable int id ){
        return ResponseEntity.ok(this.blogService.getBlogById(id));
    }
    @GetMapping("/titre/{title}")
    public ResponseEntity<List<BlogDto>> getBlogByTitle(@PathVariable String title){
        return ResponseEntity.ok(this.blogService.getBlogsByTitle(title));
    }
    @GetMapping("/owner/{id}")
    public ResponseEntity<List<BlogDto>> getBlogByOwnerId(@PathVariable int id){
        return ResponseEntity.ok(this.blogService.getBlogsByOwner(id));
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BlogDto>> getBlogByCategory(@PathVariable String category){
        return ResponseEntity.ok(this.blogService.getBlogByCategory(category));
    }
    @PostMapping("/")
    public ResponseEntity<BlogDto> createBlog(@RequestBody BlogDto blogDto){
        return ResponseEntity.status(201).body(this.blogService.createBlog(blogDto));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<BlogDto> updateBlog(@RequestBody BlogDto blogDto,@PathVariable int id){
        return ResponseEntity.ok(this.blogService.updateBlog(blogDto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBlog(@PathVariable int id ){
        this.blogService.deleteBlog(id);
        return ResponseEntity.status(204).build();
    }
}
