package com.aseds.usermanagementmicroservice.services;

import com.aseds.usermanagementmicroservice.enums.Roles;
import com.aseds.usermanagementmicroservice.mappers.implementation.AdminMapper;
import com.aseds.usermanagementmicroservice.mappers.implementation.UserMapper;
import com.aseds.usermanagementmicroservice.mappers.implementation.UserMapperDTO;
import com.aseds.usermanagementmicroservice.model.AbstractUser;
import com.aseds.usermanagementmicroservice.model.dto.RegisterRequest;
import com.aseds.usermanagementmicroservice.model.dto.UserDTO;
import com.aseds.usermanagementmicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final UserMapperDTO userMapperDTO;

    @Autowired
    public AuthService(UserRepository userRepository, UserMapper userMapper, AdminMapper adminMapper, UserMapperDTO userMapperDTO) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.adminMapper = adminMapper;
        this.userMapperDTO = userMapperDTO;
    }

    public boolean isUserExists(String identifier) {
        if (isNotValidIdentifier(identifier)) {
            return false;
        }
        return userRepository.findUserByEmail(identifier).isPresent() ||
                userRepository.findUserByPhone(identifier).isPresent();
    }

    private boolean isNotValidIdentifier(String identifier) {
        return identifier == null || identifier.isBlank();
    }

    public UserDTO registerUser(RegisterRequest registerRequest) {
        validateUserRegistration(registerRequest);

        Roles role = determineUserRole(registerRequest.getRole());
        AbstractUser user = createUser(registerRequest, role);
        AbstractUser savedUser = userRepository.save(user);

        return userMapperDTO.mapTo(savedUser);
    }

    private void validateUserRegistration(RegisterRequest registerRequest) {
        if (registerRequest.getEmail() != null && isUserExists(registerRequest.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        if (registerRequest.getPhone() != null && isUserExists(registerRequest.getPhone())) {
            throw new IllegalArgumentException("User with this phone already exists");
        }
    }

    private Roles determineUserRole(Roles requestedRole) {
        return (requestedRole == Roles.USER || requestedRole == null) ? Roles.USER : Roles.ADMIN;
    }

    private AbstractUser createUser(RegisterRequest registerRequest, Roles role) {
        AbstractUser user;

        if (role == Roles.USER) {
            user = userMapper.mapFrom(registerRequest);
        } else {
            user = adminMapper.mapFrom(registerRequest);
        }

        user.setCreatedAt(new Date());
        user.setRole(role);

        return user;
    }


    public AbstractUser getUserByIdentifier(String identifier) {
        if (isNotValidIdentifier(identifier)) {
            throw new IllegalArgumentException("Invalid identifier: cannot be null or blank");
        }

        return findUserByEmailOrPhone(identifier);
    }

    private AbstractUser findUserByEmailOrPhone(String identifier) {
        return userRepository.findUserByEmail(identifier)
                .orElseGet(() -> userRepository.findUserByPhone(identifier)
                        .orElseThrow(() -> new IllegalArgumentException("User not found with identifier: " + identifier))
                );
    }



}
