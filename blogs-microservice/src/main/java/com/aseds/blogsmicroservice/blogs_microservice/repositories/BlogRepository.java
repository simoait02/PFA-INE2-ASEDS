package com.aseds.blogsmicroservice.blogs_microservice.repositories;

import com.aseds.blogsmicroservice.blogs_microservice.models.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    List<BlogEntity> findByTitle(String title);
    List<BlogEntity> findByOwnerid(Integer id);
    List<BlogEntity> findByCategory(String category);
}
