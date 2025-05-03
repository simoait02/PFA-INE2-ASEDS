package com.aseds.userauthmicroservice.model;

import lombok.*;
import com.aseds.userauthmicroservice.enums.Roles;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String profilePictureUrl;
    private Roles role;
    private Date birthDate;
}
