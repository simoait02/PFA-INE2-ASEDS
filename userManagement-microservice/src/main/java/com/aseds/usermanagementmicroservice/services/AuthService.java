package com.aseds.usermanagementmicroservice.services;

import com.aseds.usermanagementmicroservice.enums.Roles;
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
    private final UserMapperDTO userMapperDTO;

    @Autowired
    public AuthService(UserRepository userRepository, UserMapper userMapper, UserMapperDTO userMapperDTO) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        if (registerRequest.getEmail() != null && isUserExists(registerRequest.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        if (registerRequest.getPhone() != null && isUserExists(registerRequest.getPhone())) {
            throw new IllegalArgumentException("User with this phone already exists");
        }

        AbstractUser user = userMapper.mapFrom(registerRequest);
        user.setCreatedAt(new Date());
        user.setRole(Roles.USER);

        AbstractUser savedUser = userRepository.save(user);

        return userMapperDTO.mapTo(savedUser);
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
