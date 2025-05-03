package com.aseds.userauthmicroservice.model;

import com.aseds.userauthmicroservice.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbstractUser {

    private Long id;
    private Date birthDate;
    private String profilePictureUrl;
    private String username;
    private String password;
    private String email;
    private String phone;

    private Roles role;

    private Date createdAt;
}
