package com.aseds.videomicroservice.model.dto;

import com.aseds.videomicroservice.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoDTO {
    private String videoId;
    private String title;
    private int channelId;
    private String videoUrl;
    private LocalDateTime uploadDate;
    private int duration;
    private String[] resolutions;
    private int views;
    private int likes;
    private String[] tags;
    private List<Comment> comments;
}
