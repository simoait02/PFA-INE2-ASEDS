package com.aseds.streammicroservice.model;

import com.aseds.streammicroservice.enums.States;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "streams")
public class StreamEntity {

    @Id
    private String id;

    @NotNull
    private Integer channelId;

    @NotBlank
    private String streamKey;

    @NotBlank
    private String title;

    private String description;

    private String[] tags;

    @NotNull
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @PositiveOrZero
    private Long viewCount;

    private States state;

    private int likes;

    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
