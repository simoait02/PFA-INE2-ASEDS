package com.aseds.usermanagementmicroservice.model.dto;

import com.aseds.usermanagementmicroservice.enums.Roles;
import lombok.*;
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
