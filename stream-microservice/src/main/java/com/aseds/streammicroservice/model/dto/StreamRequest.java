package com.aseds.streammicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamRequest {
    private String id;
    private Integer channelId;
    private String title;
    private String description;
    private String[] tags;
}
