package com.aseds.blogsmicroservice.blogs_microservice.mappers;

import com.aseds.blogsmicroservice.blogs_microservice.models.BlogEntity;
import com.aseds.blogsmicroservice.blogs_microservice.models.dto.BlogDto;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {

    public BlogDto toDto(BlogEntity blogEntity){
        return BlogDto.builder()
                .id(blogEntity.getId())
                .title(blogEntity.getTitle()).
                category(blogEntity.getCategory())
                .content(blogEntity.getContent())
                .owner_id(blogEntity.getOwnerid())
                .build();
    }

    public BlogEntity toEntity(BlogDto blogDto){
        return BlogEntity.builder()
                .category(blogDto.getCategory())
                .title(blogDto.getTitle())
                .ownerid(blogDto.getOwner_id())
                .content(blogDto.getContent())
                .build();
    }

    public void updateEntityFromDto(BlogEntity blogEntity,BlogDto blogDto){
        blogEntity.setCategory(blogDto.getCategory()!=null ? blogDto.getCategory() : blogEntity.getCategory());
        blogEntity.setContent(blogDto.getContent()!=null ? blogDto.getContent() : blogEntity.getContent());
        blogEntity.setTitle(blogDto.getTitle()!=null ? blogDto.getTitle() : blogEntity.getTitle());
    }
}
