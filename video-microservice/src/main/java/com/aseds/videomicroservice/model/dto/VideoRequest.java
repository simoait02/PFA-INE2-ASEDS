package com.aseds.videomicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoRequest {
    private String videoId;
    private String title;
    private String videoUrl;
    private String[] tags;
    private Long uploaderId;
    private LocalDateTime uploadDate;
    private int duration;
    private String[] resolutions;
}
