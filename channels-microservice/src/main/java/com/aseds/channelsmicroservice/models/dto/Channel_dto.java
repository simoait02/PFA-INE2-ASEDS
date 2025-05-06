package com.aseds.channelsmicroservice.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel_dto {
    private int id;
    private String description;
    private int owner_id;
    private String name;
    private int nomberofsubscriptions;
}
