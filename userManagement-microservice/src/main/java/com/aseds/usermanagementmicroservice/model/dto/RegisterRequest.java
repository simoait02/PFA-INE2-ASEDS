package com.aseds.usermanagementmicroservice.model.dto;

import com.aseds.usermanagementmicroservice.enums.Roles;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String phone;
    private String password;
    private Date birthDate;
    private Roles role;
}