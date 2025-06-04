package com.aseds.blogsmicroservice.blogs_microservice.services;

import com.aseds.blogsmicroservice.blogs_microservice.mappers.BlogMapper;
import com.aseds.blogsmicroservice.blogs_microservice.models.BlogEntity;
import com.aseds.blogsmicroservice.blogs_microservice.models.dto.BlogDto;
import com.aseds.blogsmicroservice.blogs_microservice.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private final BlogRepository repository;
    private final BlogMapper mapper;
    @Autowired
    public BlogService(BlogRepository blogRepository,BlogMapper blogMapper){
        this.repository=blogRepository;
        this.mapper=blogMapper;
    }

    public List<BlogDto> getAllBlogs(){
        return this.repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());

    }
    public BlogDto getBlogById(int id ){
        return this.repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("BLOG NOT FOUND"));

    }
    public List<BlogDto> getBlogsByTitle(String title){
        return this.repository.findByTitle(title)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public List<BlogDto> getBlogsByOwner(Integer id){
        return this.repository.findByOwnerid(id)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }
    public List<BlogDto> getBlogByCategory(String category){
        return this.repository.findByCategory(category)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public BlogDto createBlog(BlogDto blogDto){
        if(validateBlog(blogDto)){
            return mapper.toDto(this.repository.save(mapper.toEntity(blogDto)));

        }
        throw new IllegalArgumentException("BLOG NOT VALID");

    }

    public  BlogDto updateBlog(BlogDto blogDto, int id){
            BlogEntity blog=this.repository.findById(id).get();
            this.mapper.updateEntityFromDto(blog,blogDto);
            this.repository.save(blog);
            return this.mapper.toDto(blog);

    }

    public void deleteBlog(int id ){
        this.repository.deleteById(id);
    }
    public boolean validateBlog(BlogDto blogDto){
        if(blogDto ==null){
            return false;
        }
        return blogDto.getOwner_id()!=null && blogDto.getTitle()!=null;
    }

}
