package com.aseds.blogsmicroservice.blogs_microservice.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDto {
    private int id;
    private String title;
    private String content;
    private String category;
    private Integer owner_id;
}
