package com.aseds.channelsmicroservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Channels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private int ownerId;
    private String name;
    @ElementCollection
    private List<Integer> subscriptions;
}
