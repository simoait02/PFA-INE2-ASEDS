package com.aseds.videomicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @NotBlank
    private String videoId;

    @NotBlank
    private String content;

    @NotBlank
    private Long uploaderId;

    @NotNull
    private LocalDateTime uploadDate;
}