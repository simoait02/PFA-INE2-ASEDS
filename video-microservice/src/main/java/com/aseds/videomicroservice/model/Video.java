package com.aseds.videomicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "videos")
public class Video {

    @Id
    private String videoId;

    @NotBlank
    private String title;

    @NotBlank
    private Long uploaderId;

    @NotNull
    private LocalDateTime uploadDate;

    @Min(0)
    private int duration;

    private String[] resolutions;

    @NotBlank
    private String videoUrl;

    @Min(0)
    private int views;

    @Min(0)
    private int likes;

    private List<Comment> comments;

    private String[] tags;
}