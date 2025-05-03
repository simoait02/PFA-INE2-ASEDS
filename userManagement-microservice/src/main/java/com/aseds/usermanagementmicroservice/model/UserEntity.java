package com.aseds.usermanagementmicroservice.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter

public class UserEntity extends AbstractUser {

    private String bio;

    @ElementCollection
    private List<Long> subscriptions;
}
