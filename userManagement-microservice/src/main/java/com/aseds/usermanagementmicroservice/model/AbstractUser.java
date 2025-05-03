package com.aseds.usermanagementmicroservice.model;

import com.aseds.usermanagementmicroservice.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Date birthDate;
    protected String profilePictureUrl;
    protected String username;
    protected String password;

    @Column(unique = true, nullable = false)
    protected String email;

    @Column(unique = true, nullable = false)
    protected String phone;

    @Enumerated(EnumType.STRING)
    protected Roles role;

    protected Date createdAt;
}
