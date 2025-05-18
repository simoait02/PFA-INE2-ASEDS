package com.aseds.streammicroservice.model.dto;

import com.aseds.streammicroservice.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StreamDTO {
    private String id;
    private String title;
    private String streamKey;
    private String description;
    private String[] tags;
    private String viewCount;
    private int channelId;
    private int likesCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Comment> comments = new ArrayList<>();

}
