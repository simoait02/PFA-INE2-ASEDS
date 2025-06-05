package com.aseds.blogsmicroservice.blogs_microservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private Integer ownerid;
    private String category;

}
